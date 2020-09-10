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
    //region WORK WITH BUFFER
    private val bfr = Buffer()
    val buffer: LiveData<String> = bfr.out
    fun setSymbol(symbol: Symbols){
        if (state == RESULT) clear()
        bfr.symbol(symbol)
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
    //region WORK WITH ALU<--->BUFFER
    private enum class States {
        CLEARED, VALUE_A, OPERATOR, RESULT
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
    fun setOperator(operator: Operators){
        bfr.value?.let {
            val value = it.fromDisplayString()
            bfr.clear()
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
            val input = bfr.value ?: buffer.value
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
                this.bfr.clear()
            }
        }
        //if (state == RESULT) out.postValue(result?.toDisplayString())
    }
    fun percent(){
        bfr.value?.let{
            val value = it.fromDisplayString()
            bfr.clear()
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
    //endregion
    //region  WORK WITH HISTORY ALU<--->HISTORY
    val history: MutableLiveData<Operation> = MutableLiveData()
    fun historyClear(){
        history.postValue(null)
    }
    //endregion
    //ALL
    fun clear(){
        bfr.clear()
        history.postValue(null)
        a = BigDecimal.ZERO
        op = null
        b = null
        result = null
        state = CLEARED
    }
}
