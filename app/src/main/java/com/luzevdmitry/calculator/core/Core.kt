package com.luzevdmitry.calculator.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.luzevdmitry.calculator.app.AppManager
import com.luzevdmitry.calculator.model.Dao
import com.luzevdmitry.calculator.model.Record
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Core(private val dao: Dao) {
    private val buffer: Buffer
    val bufferOut: LiveData<String>
    private val memory: Memory
    val memoryOut: MutableLiveData<String>
    private var binary: BinaryOperation? = null
    private var last: Operation? = null
    val operationOut: MutableLiveData<Operation> = MutableLiveData()
    private var bufferClearRequest: Boolean
    init {
        val state = AppManager.restoreState()
        buffer = Buffer(state.buffer ?: Value())
        bufferOut = buffer.out
        bufferClearRequest = state.bufferClearRequest
        memory = Memory(state.memory)
        memoryOut = memory.out
        if (state.binary is BinaryOperation){
            binary = state.binary
        }
        state.last?.let {
            last = it
        }
        if (binary != null) operationOut.postValue(binary)
        else operationOut.postValue(last)
    }
    //region WORK WITH BUFFER
    fun symbol(sym: Char) {
        if (bufferClearRequest) {
            clearBuffer()
        }
        buffer.symbol(sym)
    }
    fun negative() {buffer.negative()}
    fun backspace() {buffer.backspace()}
    fun clearBuffer(){
        buffer.clear()
        bufferClearRequest = false
        AppManager.saveBufferClearRequest(bufferClearRequest)
    }
    fun setBufferValue(value: Value){
        buffer.setValue(value)
        bufferClearRequest = false
        AppManager.saveBufferClearRequest(bufferClearRequest)
    }
    //endregion
    //region WORK WITH MEMORY<--->BUFFER
    fun memoryClear() = memory.clear()
    fun memoryPlus() = memory.pls(buffer.getValue())
    fun memoryMinus() = memory.mns(buffer.getValue())
    fun memoryRestore() = buffer.setValue(memory.get())
    //endregion
    //region WORK WITH OPERATIONS<--->BUFFER
    fun clear() {
        buffer.clear()
        binary = null
        last = null
        operationOut.postValue(null)
        AppManager.saveBinary(null)
        AppManager.saveLast(null)
    }
    fun result() {
        if (binary != null) {
            binary!!.b = buffer.getValue()
            buffer.setValue(binary!!.result!!)
            last = binary
            binary = null
            operationOut.postValue(last)
            saveToHistory(last)
        } else {
            if (last != null) {
                last!!.a = last!!.result!!
                buffer.setValue(last!!.result!!)
                operationOut.postValue(last)
                saveToHistory(last)
            }
        }
        bufferClearRequest = true
        AppManager.saveBinary(binary)
        AppManager.saveLast(last)
        AppManager.saveBufferClearRequest(bufferClearRequest)
    }
    fun operation(op: Operation) {
        if (binary == null) {
            newOperation(op)
        } else {
            binary!!.b = buffer.getValue()
            buffer.setValue(binary!!.result!!)
            last = binary
            binary = null
            operationOut.postValue(last)
            saveToHistory(last)
            newOperation(op)
        }
        bufferClearRequest = true
        AppManager.saveBinary(binary)
        AppManager.saveLast(last)
        AppManager.saveBufferClearRequest(bufferClearRequest)
    }
    private fun newOperation(op: Operation) {
        op.a = buffer.getValue()
        when (op) {
            is UnaryOperation -> {
                buffer.setValue(op.result!!)
                last = op
                operationOut.postValue(last)
            }
            is BinaryOperation -> {
                binary = op
                operationOut.postValue(binary)
            }
        }
    }
    fun percent() {
        if (binary != null) {
            val prc = buffer.getValue()
            binary!!.b = binary!!.a!! * prc * Value(0.01)
            buffer.setValue(binary!!.result!!)
            last = binary
            binary = null
            operationOut.postValue(last)
            saveToHistory(last)
            AppManager.saveBinary(binary)
            AppManager.saveLast(last)
        }
    }
    private fun saveToHistory(op: Operation?){
        op?.let {
            GlobalScope.launch { dao.insertHistoryRecord(Record(op = it)) }
        }
    }
    //endregion
}