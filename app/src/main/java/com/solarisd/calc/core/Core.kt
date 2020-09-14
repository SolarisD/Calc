package com.solarisd.calc.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Core {
    //region WORK WITH BUFFER
    private val b = Buffer()
    val buffer: LiveData<String> = b.out
    fun symbol(sym: Char){
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
    private var m = Memory()
    val memory: MutableLiveData<String> = m.out
    fun memoryClear() = m.clear()
    fun memoryPlus() = m.pls(b.getDouble())
    fun memoryMinus() = m.mns(b.getDouble())
    fun memoryRestore() = m.data?.let{
        b.setDouble(it)
    }
    //endregion
    //region WORK WITH OPERATIONS<--->BUFFER
    val history: MutableLiveData<Operation> = MutableLiveData()
    private var binary: BinaryOperation? = null
    private var last: Operation? = null
    private var bufferClearRequest = false
    fun clear(){
        binary = null
        last = null
        history.postValue(null)
        b.clear()
    }
    fun result(){
        if (binary != null){
            binary!!.b = b.getDouble()
            b.setDouble(binary!!.result!!)
            last = binary
            binary = null
            history.postValue(last)
        } else {
            if (last != null){
                last!!.a = last!!.result!!
                b.setDouble(last!!.result!!)
                history.postValue(last)
            }
        }
        bufferClearRequest = true
    }
    fun operation(op: Operation){
        if (binary == null){
            newOperation(op)
        }
        else {
            binary!!.b = b.getDouble()
            b.setDouble(binary!!.result!!)
            last = binary
            binary = null
            history.postValue(last)
            newOperation(op)
        }
        bufferClearRequest = true
    }
    private fun newOperation(op: Operation){
        op.a = b.getDouble()
        when(op){
            is UnaryOperation->{
                b.setDouble(op.result!!)
                last = op
                history.postValue(last)
            }
            is BinaryOperation->{
                binary = op
                history.postValue(binary)
            }
        }
    }
    fun percent(){
        if (binary != null){
            val prc = b.getDouble()
            binary!!.b = binary!!.a!! * prc * 0.01
            b.setDouble(binary!!.result!!)
            last = binary
            binary = null
            history.postValue(last)
        }
    }
    //endregion
}