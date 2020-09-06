package com.solarisd.calc.core

import com.solarisd.calc.core.enums.Operators
import com.solarisd.calc.core.enums.States
import java.math.BigDecimal
import java.math.MathContext
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

class FS {
    private var acc: BigDecimal = BigDecimal.ZERO
    private var op: Operators? = null
    var lastOperation: Operation? = null
        private set
    var state: States = States.CLEARED
        private set
    val value: BigDecimal
        get() = acc

    private fun equal(op: Operators, a: BigDecimal, b: BigDecimal?): BigDecimal {
        val res = when (op) {
            Operators.PLUS -> a.add(b)
            Operators.MINUS -> a.subtract(b)
            Operators.MULTIPLY -> a.multiply(b)
            Operators.DIVIDE -> a.divide(b, MathContext.DECIMAL64)
            Operators.SQR -> a.pow(2)
            Operators.SQRT -> sqrt(a.toDouble()).toBigDecimal()
            Operators.SIN -> sin(a.toDouble() * Math.PI/180).toBigDecimal()
            Operators.COS -> cos(a.toDouble() * Math.PI/180).toBigDecimal()
            Operators.TAN -> tan(a.toDouble() * Math.PI/180).toBigDecimal()
        }
        lastOperation = Operation(op, a, b, res)
        return res
    }
    fun operator(op: Operators, input: BigDecimal?){
        when(state){
            States.CLEARED ->{
                acc = input ?: BigDecimal.ZERO
                this.op = op
                state = if (op.unary){
                    acc = equal(op, acc, null)
                    States.RESULT
                } else {
                    lastOperation = Operation(op, acc)
                    States.OPERATOR_SAVED
                }
            }
            States.VALUE_SAVED -> {
                this.op = op
                state = if (op.unary){
                    acc = equal(op, acc, null)
                    States.RESULT
                } else {
                    lastOperation = Operation(op, acc)
                    States.OPERATOR_SAVED
                }
            }
            States.OPERATOR_SAVED ->{
                input?.let {
                    acc = equal(this.op!!, acc, it)
                }
                this.op = op
                state = if (op.unary){
                    acc = equal(op, acc, null)
                    States.RESULT
                } else {
                    States.OPERATOR_SAVED
                }
            }
            States.RESULT ->{
                this.op = op
                state = if (op.unary){
                    acc = equal(op, acc, null)
                    States.RESULT
                } else {
                    lastOperation = Operation(op, acc)
                    States.OPERATOR_SAVED
                }
            }
        }
    }
    fun clear(){
        acc = BigDecimal.ZERO
        op = null
        lastOperation = null
        state = States.CLEARED
    }
    fun result(input: BigDecimal?){
        when (state){
            States.CLEARED -> {
                acc = input ?: BigDecimal.ZERO
                state = States.VALUE_SAVED
            }
            States.VALUE_SAVED -> {
                state = States.VALUE_SAVED
            }
            States.OPERATOR_SAVED -> {
                if (input == null){
                    acc = equal(this.op!!, acc, acc)
                } else{
                    acc = equal(this.op!!, acc, input)
                }
                state = States.RESULT
            }
            States.RESULT -> {
                acc = equal(lastOperation!!.op, acc, lastOperation!!.b)
            }
        }
    }
    fun negative(){
        when(state){
            States.CLEARED, States.OPERATOR_SAVED ->{ }
            else->{ acc = -acc }
        }
    }
}