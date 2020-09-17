package com.solarisd.calc.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Core(state: State = State()) {
    private val b = Buffer()
    val buffer: LiveData<String> = b.out
    private var m = Memory()
    val memory: MutableLiveData<String> = m.out
    val operation: MutableLiveData<Operation> = MutableLiveData()
    private var binary: BinaryOperation? = null
    private var last: Operation? = null
    private var bufferClearRequest = false
    val state: State
        get() = State(b.getDouble().toDisplayString(), m.data.toDisplayString(), binary, last)

    init {
        state.buffer?.let {
            b.setDouble(it.toDoubleFromDisplay())
        }
        state.memory?.let {
            m.clear()
            m.pls(it.toDoubleFromDisplay())
        }
        state.binaryOperation?.let {
            binary = state.binaryOperation
        }
        state.lastOperation?.let {
            last = state.lastOperation
        }
        if (binary != null) operation.postValue(binary)
        else operation.postValue(last)
    }
    //region WORK WITH BUFFER
    fun symbol(sym: Char) {
        if (bufferClearRequest) {
            b.clear()
            bufferClearRequest = false
        }
        b.symbol(sym)
    }

    fun negative() = b.negative()
    fun backspace() = b.backspace()

    //endregion
    //region WORK WITH MEMORY<--->BUFFER
    fun memoryClear() = m.clear()
    fun memoryPlus() = m.pls(b.getDouble())
    fun memoryMinus() = m.mns(b.getDouble())
    fun memoryRestore() = m.data?.let {
        b.setDouble(it)
    }

    //endregion
    //region WORK WITH OPERATIONS<--->BUFFER
    fun clear() {
        binary = null
        last = null
        operation.postValue(null)
        b.clear()
    }

    fun result() {
        if (binary != null) {
            binary!!.b = b.getDouble()
            b.setDouble(binary!!.result!!)
            last = binary
            binary = null
            operation.postValue(last)
        } else {
            if (last != null) {
                last!!.a = last!!.result!!
                b.setDouble(last!!.result!!)
                operation.postValue(last)
            }
        }
        bufferClearRequest = true
    }

    fun operation(op: Operation) {
        if (binary == null) {
            newOperation(op)
        } else {
            binary!!.b = b.getDouble()
            b.setDouble(binary!!.result!!)
            last = binary
            binary = null
            operation.postValue(last)
            newOperation(op)
        }
        bufferClearRequest = true
    }

    private fun newOperation(op: Operation) {
        op.a = b.getDouble()
        when (op) {
            is UnaryOperation -> {
                b.setDouble(op.result!!)
                last = op
                operation.postValue(last)
            }
            is BinaryOperation -> {
                binary = op
                operation.postValue(binary)
            }
        }
    }

    fun percent() {
        if (binary != null) {
            val prc = b.getDouble()
            binary!!.b = binary!!.a!! * prc * 0.01
            b.setDouble(binary!!.result!!)
            last = binary
            binary = null
            operation.postValue(last)
        }
    }
    //endregion
}