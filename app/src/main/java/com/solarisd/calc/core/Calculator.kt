package com.solarisd.calc.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.solarisd.calc.core.enums.Operators
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
    private var currentOp: Operation? = null
    private var prevOp: Operation? = null
    private var bufferClearRequest = false
    fun clear(){
        bfr.clear()
        currentOp = null
        prevOp = null
    }
    fun result(){
        if (currentOp != null){
            val b = bfr.value?.fromDisplayString() ?: BigDecimal.ZERO
            val result = currentOp!!.result(b)
            postToHistory(currentOp!!)
            if(result != null) {
                bfr.setDecimal(result)
                prevOp = currentOp
                currentOp = null
            } else {
                clear()
            }
        } else {
            prevOp?.let{
                val a = it.result()
                val op = it.op
                val b = it.b
                currentOp = Operation(a!!, op)
                val result = currentOp!!.result(b)
                postToHistory(currentOp!!)
                if(result != null) {
                    bfr.setDecimal(result)
                    prevOp = currentOp
                    currentOp = null
                } else {
                    clear()
                }
            }
        }
    }
    fun operator(op: Operators){
        if (currentOp == null){
            val a = bfr.value?.fromDisplayString() ?: BigDecimal.ZERO
            currentOp = Operation(a, op)
            val result = currentOp?.result()
            postToHistory(currentOp!!)
            if(result != null) {
                bfr.setDecimal(result)
                prevOp = currentOp
                currentOp = null
            } else {
                bufferClearRequest = true
            }
        } else {
            val b = bfr.value?.fromDisplayString() ?: BigDecimal.ZERO
            val result = currentOp?.result(b)
            postToHistory(currentOp!!)
            if(result != null) {
                bfr.setDecimal(result)
                prevOp = currentOp
                currentOp = null
            } else {
                clear()
            }
        }
    }
    fun percent(){
        if (currentOp != null){
            val prc = bfr.value?.fromDisplayString() ?: BigDecimal.ZERO
            val b = currentOp!!.a.multiply(prc.multiply(BigDecimal("0.01")))
            val result = currentOp?.result(b)
            postToHistory(currentOp!!)
            if(result != null) {
                bfr.setDecimal(result)
                prevOp = currentOp
                currentOp = null
            } else {
                clear()
            }

        } else {
            val prc = bfr.value?.fromDisplayString() ?: BigDecimal.ZERO
            /*val result = prc.multiply(BigDecimal("0.01"))
            bfr.setDecimal(result)
            prevOp = currentOp
            currentOp = null*/
        }
    }
    //endregion
    //region  WORK WITH HISTORY HISTORY<---->OPERATIONS
    val history: MutableLiveData<OperationHistory> = MutableLiveData()
    private fun postToHistory(op: Operation){
        history.postValue(OperationHistory(op.op, op.a, op.b, op.result()))
    }
    fun historyClear(){
        history.postValue(null)
    }
    //endregion
}