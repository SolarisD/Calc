package com.solarisd.calc.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.solarisd.calc.core.enums.Operators
import com.solarisd.calc.core.enums.Symbols
import java.math.BigDecimal

class Calculator2 {
    //region WORK WITH BUFFER
    private val bfr = Buffer()
    val buffer: LiveData<String> = bfr.out
    fun clear(){
        bfr.clear()
        currentOp = null
        prevOp = null
    }
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

    var currentOp: Operation? = null
    var prevOp: Operation? = null
    var bufferClearRequest = false
    fun result(){
        currentOp?.let {
            val b = bfr.value?.fromDisplayString() ?: BigDecimal.ZERO
            val result = currentOp?.result(b)
            if(result != null) {
                bfr.setDecimal(result)
                prevOp = currentOp
                currentOp = null
            } else {
                clear()
            }
        }
    }
    fun operator(op: Operators){
        if (currentOp == null){
            val a = bfr.value?.fromDisplayString() ?: BigDecimal.ZERO
            currentOp = Operation(a, op)
            val result = currentOp?.result()
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
            if(result != null) {
                bfr.setDecimal(result)
                prevOp = currentOp
                currentOp = null
            } else {
                clear()
            }
        }
    }


    //region  WORK WITH HISTORY ALU<--->HISTORY
    val history: MutableLiveData<OperationHistory> = MutableLiveData()
    fun historyClear(){
        history.postValue(null)
    }
    //endregion
}
