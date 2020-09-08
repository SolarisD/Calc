/*
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
        //if (fsm.a != States.VALUE_SAVED || fsm.state == States.RESULT) clear()
        buffer.symbol(symbol)
        if (buffer.value!!.length > 2){
            value.postValue(buffer.value?.fromDisplayString()?.toDisplayString())
        } else {
            value.postValue(buffer.value)
        }
    }
    fun operator(op: Operators){
        val value = buffer.value?.fromDisplayString() ?: BigDecimal.ZERO
        if (fsm.a == null) {
            fsm.a = value
            fsm.op = op
            if (op.unary) fsm.equal()
        }else{

        }
        op.unary



        fsm.operator(operator, buffer.value?.toBigDecimal())
        buffer.clear()
        val result = fsm.value
        value.postValue(result.toDisplayString())
        history.postValue(fsm.lastOperation)
        */
/*try {

        }catch (e: Exception){
            value.postValue("Error")
            history.postValue(fsm.lastOperation)
        }*//*

    }
    fun result(){
        val value = buffer.value?.fromDisplayString() ?: BigDecimal.ZERO

        try {
            fsm.result(buffer.value?.toBigDecimal())
            value.postValue(fsm.value.toDisplayString())
            history.postValue(fsm.lastOperation)
        }catch (e: Exception){
            value.postValue("Error")
            history.postValue(fsm.lastOperation)
        }

    }
    fun negative(){
        when(fsm.state){
            States.CLEARED, States.OPERATOR_SAVED -> {
                buffer.negative()
                value.postValue(buffer.value)
            }
            else -> {
                fsm.negative()
                value.postValue(fsm.value.toDisplayString())
            }
        }
    }
    fun backspace(){
        when(fsm.state){
            States.CLEARED, States.OPERATOR_SAVED -> {
                buffer.backspace()
                value.postValue(buffer.value)
            }
            else -> {}
        }
    }
    fun percent(){
        val src = fsm.value
        val input = buffer.value?.toBigDecimal() ?: BigDecimal.ZERO
        buffer.clear()
        val prc = src.multiply(BigDecimal(0.01)).multiply(input).setScale(10, RoundingMode.HALF_UP).stripTrailingZeros()
        fsm.result(prc)
        value.postValue(fsm.value.toDisplayString())
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

    class FSM2 {
        var a: BigDecimal? = null
        var op: Operators? = null
        var b: BigDecimal? = null
        var result: BigDecimal? = null
            private set
        fun equal(){
            result = when (op) {
                Operators.PLUS -> a?.add(b)
                Operators.MINUS -> a?.subtract(b)
                Operators.MULTIPLY -> a?.multiply(b)
                Operators.DIVIDE -> a?.divide(b, MathContext.DECIMAL64)
                Operators.SQR -> a?.pow(2)
                */
/*Operators.SQRT -> sqrt(a?.toDouble()).toBigDecimal()
                Operators.SIN -> sin(a.toDouble() * Math.PI / 180).toBigDecimal()
                Operators.COS -> cos(a.toDouble() * Math.PI / 180).toBigDecimal()
                Operators.TAN -> tan(a.toDouble() * Math.PI / 180).toBigDecimal()*//*

                else -> null
            }
        }
        fun clear(){
            a = null
            op = null
            b = null
        }

    }
}*/
