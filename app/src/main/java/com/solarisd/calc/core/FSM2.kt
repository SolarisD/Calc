package com.solarisd.calc.core

import com.solarisd.calc.core.enums.Operators
import java.lang.Math.*
import java.math.BigDecimal
import java.math.MathContext

class FSM2 {
    var a: BigDecimal = BigDecimal.ZERO
        private set
    var op: Operators? = null
        private set
    var b: BigDecimal? = null
        private set
    var result: BigDecimal? = null
        private set
    var state: States = States.CLEARED
        private set
    private fun equal(){
        result = when (op) {
            Operators.PLUS -> a.add(b)
            Operators.MINUS -> a.subtract(b)
            Operators.MULTIPLY -> a.multiply(b)
            Operators.DIVIDE -> a.divide(b, MathContext.DECIMAL64)
            Operators.SQR -> a.pow(2)
            Operators.SQRT -> sqrt(a.toDouble()).toBigDecimal()
            Operators.SIN -> sin(a.toDouble() * Math.PI / 180).toBigDecimal()
            Operators.COS -> cos(a.toDouble() * Math.PI / 180).toBigDecimal()
            Operators.TAN -> tan(a.toDouble() * Math.PI / 180).toBigDecimal()
            else -> null
        }
    }
    fun setValue(value: BigDecimal){
        when(state){
            States.CLEARED->{
                a = value
                state = States.VALUE_A
            }
            States.VALUE_A->{
                clear()
                a = value
                state = States.VALUE_A
            }
            States.OPERATOR->{
                b = value
                equal()
                state = States.RESULT
            }
            States.RESULT->{
                clear()
                a = value
                state = States.VALUE_A
            }
        }
    }
    fun setOp(value: Operators){
        when(state){
            States.CLEARED->{
                op = value
                if (value.unary) equal()
            }
            States.VALUE_A->{
                op = value
                if (value.unary) {equal(); state = States.RESULT}
                else {state = States.OPERATOR}
            }
            States.OPERATOR->{
                op = value
                if (value.unary) {equal(); state = States.RESULT}
                else {state = States.OPERATOR}
            }
            States.RESULT->{
                a = result ?: BigDecimal.ZERO
                op = value
                if (value.unary) {equal(); state = States.RESULT}
                else {state = States.OPERATOR}
            }
        }
    }
    fun clear(){
        a = BigDecimal.ZERO
        op = null
        b = null
        result = null
        state = States.CLEARED
    }
    enum class States {
        CLEARED, VALUE_A, OPERATOR, RESULT
    }
}