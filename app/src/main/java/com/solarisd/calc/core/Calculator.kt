package com.solarisd.calc.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.solarisd.calc.core.enums.Symbols
import java.math.BigDecimal


class Calculator {
    //region WORK WITH BUFFER
    private val bfr = Buffer()
    val buffer: LiveData<String> = bfr.out
    fun symbol(sym: Symbols){
        if (bufferClearRequest) bfr.clear(); bufferClearRequest = false
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
        bfr.value?.let {
            m.pls(it.fromDisplayString())
        }
    }
    fun memoryMinus(){
        bfr.value?.let {
            m.mns(it.fromDisplayString())
        }
    }
    fun memoryRestore(){
        m.data?.let{
            bfr.setDecimal(it)
        }
    }
    //endregion
    //region WORK WITH OPERATIONS<--->BUFFER
    private var binary: BinaryOperation? = null
    private var last: RootOperation? = null
    private var bufferClearRequest = false
    fun clear(){
        bfr.clear()
       /* binary = null
        prev = null*/
    }
    fun result(){
        if (binary != null){
            binary!!.b = bfr.value?.fromDisplayString() ?: BigDecimal.ZERO
            bfr.value = binary!!.result
            last = binary
            binary = null
        } else {
            if (last != null){
                last!!.a = last!!.result.fromDisplayString()
                bfr.value = last!!.result
            }
        }
    }
    fun operation(op: RootOperation){
        if (binary == null){
            op.a = bfr.value?.fromDisplayString() ?: BigDecimal.ZERO
            when(op){
                is UnaryOperation->{
                    bfr.value = op.result
                    last = op
                }
                is BinaryOperation->{
                    binary = op
                }
            }
            bufferClearRequest = true
        }
        else {
            /*val b = bfr.value?.fromDisplayString() ?: BigDecimal.ZERO
            val result = binary?.result(b)
            postToHistory(binary!!)
            if(result != null) {
                bfr.setDecimal(result)
                last = binary
                binary = null
            } else {
                clear()
            }*/
        }
    }
    fun percent(){
        /*if (binary != null){
            val prc = bfr.value?.fromDisplayString() ?: BigDecimal.ZERO
            val b = binary!!.a.multiply(prc.multiply(BigDecimal("0.01")))
            val result = binary?.result(b)
            postToHistory(binary!!)
            if(result != null) {
                bfr.setDecimal(result)
                last = binary
                binary = null
            } else {
                clear()
            }

        } else {
            val prc = bfr.value?.fromDisplayString() ?: BigDecimal.ZERO
            val result = prc.multiply(BigDecimal("0.01"))
            bfr.setDecimal(result)
            prevOp = currentOp
            currentOp = null
        }*/
    }
    //endregion
    //region  WORK WITH HISTORY HISTORY<---->OPERATIONS
    val history: MutableLiveData<History> = MutableLiveData()
    private fun postToHistory(){
        //history.postValue(History(op.op, op.a, op.b, op.result()))
    }
    fun historyClear(){
        history.postValue(null)
    }
    //endregion
}