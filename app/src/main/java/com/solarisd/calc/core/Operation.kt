package com.solarisd.calc.core

import com.solarisd.calc.core.enums.Operators
import java.math.BigDecimal
import java.math.MathContext
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

class Operation(val a: BigDecimal, val op: Operators) {
    var b: BigDecimal? = null
        private set

    fun result(b: BigDecimal? = null): BigDecimal? {
        if (this.b == null && b != null) this.b = b

        return if (op.unary){
            when (op) {
                Operators.SQR -> a.pow(2)
                Operators.SQRT -> sqrt(a.toDouble()).toBigDecimal()
                Operators.SIN -> sin(a.toDouble() * Math.PI / 180).toBigDecimal()
                Operators.COS -> cos(a.toDouble() * Math.PI / 180).toBigDecimal()
                Operators.TAN -> tan(a.toDouble() * Math.PI / 180).toBigDecimal()
                else-> null
            }
        } else {
            if (this.b != null) {
                when (op) {
                    Operators.PLUS -> a.add(this.b)
                    Operators.MINUS -> a.subtract(this.b)
                    Operators.MULTIPLY -> a.multiply(this.b)
                    Operators.DIVIDE -> a.divide(this.b, MathContext.DECIMAL64)
                    else -> null
                }
            } else {
                null
            }
        }
    }

    override fun toString(): String {
        val res = result()
        val postfix = if (res != null){
            if (op.unary) " = ${res.toDisplayString()}" else " ${b?.toDisplayString()} = ${res.toDisplayString()}"
        } else ""

        return if (op.unary) {
            "${op.symbol}(${a.toDisplayString()})$postfix"
        } else {
            "${a.toDisplayString()} ${op.symbol}$postfix"
        }
    }
}