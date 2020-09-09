package com.solarisd.calc.core

import androidx.lifecycle.MutableLiveData
import com.solarisd.calc.core.enums.Operators
import com.solarisd.calc.core.enums.States
import com.solarisd.calc.core.enums.Symbols
import java.lang.Exception
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

class Calculator2 {
    val value: MutableLiveData<String> = MutableLiveData()
    val memory: MutableLiveData<String> = MutableLiveData()
    val history: MutableLiveData<Operation> = MutableLiveData()
    private val buffer = InputBuffer()
    private val fsm = FSM2()
    private var m: BigDecimal? = null
    fun clear(){
        fsm.clear()
        buffer.clear()
        value.postValue(buffer.value)
        history.postValue(null)
    }
    fun symbol(symbol: Symbols){
        buffer.symbol(symbol)
        if (buffer.value!!.length > 2){
            value.postValue(buffer.value?.fromDisplayString()?.toDisplayString())
        } else {
            value.postValue(buffer.value)
        }
    }
    fun operator(op: Operators){
        buffer.value?.let {
            fsm.setValue(it.fromDisplayString())
            buffer.clear()
            if (fsm.state == FSM2.States.RESULT) value.postValue(fsm.result?.toDisplayString())
        }
        fsm.setOp(op)
        if (fsm.state == FSM2.States.RESULT) value.postValue(fsm.result?.toDisplayString())
    }
    fun result(){
        val input = buffer.value ?: value.value
        input?.let{
            fsm.setValue(it.fromDisplayString())
            buffer.clear()
        }
        if (fsm.state == FSM2.States.RESULT) value.postValue(fsm.result?.toDisplayString())
    }
    fun negative(){
        buffer.negative()
        value.postValue(buffer.value)
    }
    fun backspace(){
        buffer.backspace()
        value.postValue(buffer.value)
    }
    fun percent(){
        buffer.value?.let{
            fsm.setPercent(it.fromDisplayString())
            buffer.clear()
        }
        if (fsm.state == FSM2.States.RESULT) value.postValue(fsm.result?.toDisplayString())
    }

    fun memoryClear(){
        m = null
        memory.postValue(null)
    }
    fun memoryPlus(){
        value.value?.let {
            if (m != null) m = m!!.add(it.fromDisplayString())
            else m = it.fromDisplayString()
            memory.postValue(m?.toDisplayString())
        }
    }
    fun memoryMinus(){
        value.value?.let {
            if (m != null) m = m!!.subtract(it.fromDisplayString())
            else m = -it.fromDisplayString()
            memory.postValue(m?.toDisplayString())
        }
    }
    fun memoryRestore(){
        m?.let{
            buffer.setDecimal(it)
        }
        value.postValue(buffer.value?.fromDisplayString()?.toDisplayString())
    }

    fun historyClear(){
        history.postValue(null)
    }
}
