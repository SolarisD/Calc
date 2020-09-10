package com.solarisd.calc.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.solarisd.calc.core.Calculator.States.*
import com.solarisd.calc.core.enums.Operators
import com.solarisd.calc.core.enums.Symbols
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

class Calculator {
    private val buffer = Buffer()
    private var m: BigDecimal? = null
    val out: LiveData<String> = buffer.out
    val memory: MutableLiveData<String> = MutableLiveData()
    val history: MutableLiveData<Operation> = MutableLiveData()

    fun clear(){
        buffer.clear()
        history.postValue(null)
        a = BigDecimal.ZERO
        op = null
        b = null
        result = null
        state = CLEARED
    }
    fun historyClear(){
        history.postValue(null)
    }
    //WORK WITH BUFFER
    fun setSymbol(symbol: Symbols){
        if (state == RESULT) clear()
        buffer.symbol(symbol)
    }
    fun negative(){
        buffer.negative()
    }
    fun backspace(){
        buffer.backspace()
    }
    //WORK WITH MEMORY<--->BUFFER
    fun memoryClear(){
        m = null
        memory.postValue(null)
    }
    fun memoryPlus(){
        buffer.value?.let {
            if (m != null) m = m!!.add(it.fromDisplayString())
            else m = it.fromDisplayString()
            memory.postValue(m?.toDisplayString())
        }
    }
    fun memoryMinus(){
        buffer.value?.let {
            m = if (m != null) m!!.subtract(it.fromDisplayString())
            else -it.fromDisplayString()
            memory.postValue(m?.toDisplayString())
        }
    }
    fun memoryRestore(){
        m?.let{
            buffer.setDecimal(it)
        }
    }
    //WORK WITH ALU<--->BUFFER
    fun setOperator(operator: Operators){
        buffer.value?.let {
            val value = it.fromDisplayString()
            buffer.clear()
            when(state){
                CLEARED->{
                    a = value
                    state = VALUE_A
                }
                VALUE_A->{
                    clear()
                    a = value
                    state = VALUE_A
                }
                OPERATOR->{
                    b = value
                    equal()
                    state = RESULT
                }
                RESULT->{
                    clear()
                    a = value
                    state = VALUE_A
                }
            }
            //if (state == RESULT) out.postValue(result?.toDisplayString())
        }
        when(state){
            CLEARED->{
                op = operator
                if (operator.unary) equal()
            }
            VALUE_A->{
                op = operator
                if (operator.unary) {equal(); state = RESULT}
                else {state = OPERATOR}
            }
            OPERATOR->{
                op = operator
                if (operator.unary) {equal(); state = RESULT}
                else {state = OPERATOR}
            }
            RESULT->{
                a = result ?: BigDecimal.ZERO
                op = operator
                if (operator.unary) {equal(); state = RESULT}
                else {state = OPERATOR}
            }
        }
        //if (state == RESULT) out.postValue(result?.toDisplayString())
    }
    fun result(){
        if (state == RESULT){
            a = result ?: BigDecimal.ZERO
            equal()
        } else {
            val input = buffer.value ?: out.value
            input?.let{
                val value = it.fromDisplayString()
                when(state){
                    CLEARED->{
                        a = value
                        state = VALUE_A
                    }
                    VALUE_A->{
                        clear()
                        a = value
                        state = VALUE_A
                    }
                    OPERATOR->{
                        b = value
                        equal()
                        state = RESULT
                    }
                    RESULT->{
                        clear()
                        a = value
                        state = VALUE_A
                    }
                }
                this.buffer.clear()
            }
        }
        //if (state == RESULT) out.postValue(result?.toDisplayString())
    }
    fun percent(){
        buffer.value?.let{
            val value = it.fromDisplayString()
            buffer.clear()
            when(state){
                CLEARED->{
                    result = BigDecimal(0.01).multiply(value).setScale(10, RoundingMode.HALF_UP).stripTrailingZeros()
                    state = RESULT
                }
                VALUE_A->{
                    result = a.multiply(BigDecimal(0.01)).multiply(value).setScale(10, RoundingMode.HALF_UP).stripTrailingZeros()
                    state = RESULT
                }
                OPERATOR->{
                    b = a.multiply(BigDecimal(0.01)).multiply(value).setScale(10, RoundingMode.HALF_UP).stripTrailingZeros()
                    equal()
                    state = RESULT
                }
                RESULT->{
                    result = result!!.multiply(BigDecimal(0.01)).multiply(value).setScale(10, RoundingMode.HALF_UP).stripTrailingZeros()
                    state = RESULT
                }
            }
        }
        //if (state == RESULT) out.postValue(result?.toDisplayString())
    }

    private var a: BigDecimal = BigDecimal.ZERO
    private var op: Operators? = null
    private var b: BigDecimal? = null
    private var result: BigDecimal? = null
    private var state: States = CLEARED

    private fun equal(){
        result = when (op) {
            Operators.PLUS -> a.add(b)
            Operators.MINUS -> a.subtract(b)
            Operators.MULTIPLY -> a.multiply(b)
            Operators.DIVIDE -> a.divide(b, MathContext.DECIMAL64)
            Operators.SQR -> a.pow(2)
            Operators.SQRT -> Math.sqrt(a.toDouble()).toBigDecimal()
            Operators.SIN -> Math.sin(a.toDouble() * Math.PI / 180).toBigDecimal()
            Operators.COS -> Math.cos(a.toDouble() * Math.PI / 180).toBigDecimal()
            Operators.TAN -> Math.tan(a.toDouble() * Math.PI / 180).toBigDecimal()
            else -> null
        }
    }

    private enum class States {
        CLEARED, VALUE_A, OPERATOR, RESULT
    }
}
