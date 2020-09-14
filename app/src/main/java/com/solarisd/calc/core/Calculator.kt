package com.solarisd.calc.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.math.BigDecimal

class Calculator {
    //region WORK WITH BUFFER
    private val bfr = Buffer2()
    val buffer: LiveData<String> = bfr.out
    fun symbol(sym: Char){
        if (bufferClearRequest) {
            bfr.clear()
            bufferClearRequest = false
        }
        bfr.symbol(sym)
    }
    fun negative(){
        bfr.negative()
    }
    fun backspace(){
        bfr.backspace()
    }
    //endregion
    //region WORK WITH MEMORY<--->BUFFER
    private var m = Memory()
    val memory: MutableLiveData<String> = m.out
    fun memoryClear() {
        m.clear()
    }
    fun memoryPlus(){
        m.pls(bfr.getDouble())
    }
    fun memoryMinus(){
        m.mns(bfr.getDouble())
    }
    fun memoryRestore(){
        m.data?.let{
            bfr.setDouble(it)
        }
    }
    //endregion
    //region WORK WITH OPERATIONS<--->BUFFER
    val history: MutableLiveData<Operation> = MutableLiveData()
    private var binary: BinaryOperation? = null
    private var last: Operation? = null
    private var bufferClearRequest = false
    fun clear(){
        bfr.clear()
        binary = null
        last = null
        history.postValue(null)
    }
    fun result(){
        if (binary != null){
            binary!!.b = bfr.getDouble()
            bfr.setDouble(binary!!.result!!)
            last = binary
            binary = null
            history.postValue(last)
        } else {
            if (last != null){
                last!!.a = last!!.result!!.toDouble()
                bfr.setDouble(last!!.result!!)
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
            binary!!.b = bfr.getDouble()
            bfr.setDouble(binary!!.result!!)
            last = binary
            binary = null
            history.postValue(last)
            newOperation(op)
        }
        bufferClearRequest = true
    }
    private fun newOperation(op: Operation){
        op.a = bfr.getDouble()
        when(op){
            is UnaryOperation->{
                bfr.setDouble(op.result!!)
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
            val prc = bfr.getDouble()
            binary!!.b = binary!!.a!! * prc * 0.01
            bfr.setDouble(binary!!.result!!)
            last = binary
            binary = null
            history.postValue(last)
        }
    }
    //endregion
}