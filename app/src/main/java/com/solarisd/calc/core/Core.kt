package com.solarisd.calc.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.solarisd.calc.model.Dao
import com.solarisd.calc.model.Record
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Core(private val dao: Dao) {
    private val buffer = Buffer()
    val bufferOut: LiveData<String> = buffer.out
    private var memory = Memory()
    val memoryOut: MutableLiveData<String> = memory.out
    private var binary: BinaryOperation? = null
    private var last: Operation? = null
    val operationOut: MutableLiveData<Operation> = MutableLiveData()
    private var bufferClearRequest = false
    init {
        val storedBinry = AppManager.restoreBinary()
        val storedLast = AppManager.restoreLast()
        if (storedBinry is BinaryOperation){
            binary = storedBinry
        }
        storedLast?.let {
            last = it
        }
        if (binary != null) operationOut.postValue(binary)
        else operationOut.postValue(last)
    }
    //region WORK WITH BUFFER
    fun symbol(sym: Char) {
        if (bufferClearRequest) {
            buffer.clear()
            bufferClearRequest = false
        }
        buffer.symbol(sym)
    }
    fun negative() {buffer.negative()}
    fun backspace() {buffer.backspace()}
    //endregion
    //region WORK WITH MEMORY<--->BUFFER
    fun memoryClear() = memory.clear()
    fun memoryPlus() = memory.pls(buffer.getDouble())
    fun memoryMinus() = memory.mns(buffer.getDouble())
    fun memoryRestore() = memory.data?.let {
        buffer.setDouble(it)
    }
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
            binary!!.b = buffer.getDouble()
            buffer.setDouble(binary!!.result!!)
            last = binary
            binary = null
            operationOut.postValue(last)
            saveToHistory(last)
        } else {
            if (last != null) {
                last!!.a = last!!.result!!
                buffer.setDouble(last!!.result!!)
                operationOut.postValue(last)
                saveToHistory(last)
            }
        }
        bufferClearRequest = true
        AppManager.saveBinary(binary)
        AppManager.saveLast(last)
    }
    fun operation(op: Operation) {
        if (binary == null) {
            newOperation(op)
        } else {
            binary!!.b = buffer.getDouble()
            buffer.setDouble(binary!!.result!!)
            last = binary
            binary = null
            operationOut.postValue(last)
            saveToHistory(last)
            newOperation(op)
        }
        bufferClearRequest = true
        AppManager.saveBinary(binary)
        AppManager.saveLast(last)
    }
    private fun newOperation(op: Operation) {
        op.a = buffer.getDouble()
        when (op) {
            is UnaryOperation -> {
                buffer.setDouble(op.result!!)
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
            val prc = buffer.getDouble()
            binary!!.b = binary!!.a!! * prc * 0.01
            buffer.setDouble(binary!!.result!!)
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
            GlobalScope.launch { dao.insertHistoryRecord(Record(expression = it.toString())) }
        }
    }
    //endregion
}