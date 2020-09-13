package com.solarisd.calc.core

import java.lang.Exception
import java.math.BigDecimal
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

interface Operation {
    var a: BigDecimal?
    val isComplete: Boolean
    val result: String
}
abstract class UnaryOperation(): Operation{
    override var a: BigDecimal? = null
    override val isComplete: Boolean
        get() = a != null
    protected abstract fun equal(a: BigDecimal): BigDecimal
    override val result: String
        get() =
            if (isComplete){
                try {
                    equal(a!!).toString()
                }catch (e: Exception){
                    "Error"
                }

            } else {
                "Operation isn't initialized"
            }
}
abstract class BinaryOperation(): Operation{
    override var a: BigDecimal? = null
    var b: BigDecimal? = null
    override val isComplete: Boolean
        get() = a != null && b != null
    protected abstract fun equal(a: BigDecimal, b: BigDecimal): BigDecimal
    override val result: String
        get() =
            if (isComplete){
                try {
                    equal(a!!, b!!).toString()
                }catch (e: Exception){
                    "Error"
                }

            } else {
                "Operation isn't initialized"
            }
}

class Operations{
    class Add() : BinaryOperation(){
        override fun equal(a: BigDecimal, b: BigDecimal): BigDecimal = a.add(b)
        override fun toString(): String {
            val post = if (isComplete){
                "${b!!.toDisplayString()} = $result"
            } else {
                ""
            }
            return "${a!!.toDisplayString()} + $post"
        }
    }

    class Subtract() : BinaryOperation(){
        override fun equal(a: BigDecimal, b: BigDecimal): BigDecimal = a.subtract(b)
        override fun toString(): String {
            val post = if (isComplete){
                "${b!!.toDisplayString()} = $result"
            } else {
                ""
            }
            return "${a!!.toDisplayString()} - $post"
        }
    }

    class Multiply() : BinaryOperation(){
        override fun equal(a: BigDecimal, b: BigDecimal): BigDecimal = a.multiply(b)
        override fun toString(): String {
            val post = if (isComplete){
                "${b!!.toDisplayString()} = $result"
            } else {
                ""
            }
            return "${a!!.toDisplayString()} × $post"
        }
    }

    class Divide() : BinaryOperation(){
        override fun equal(a: BigDecimal, b: BigDecimal): BigDecimal = a.divide(b)
        override fun toString(): String {
            val post = if (isComplete){
                "${b!!.toDisplayString()} = $result"
            } else {
                ""
            }
            return "${a!!.toDisplayString()} ÷ $post"
        }
    }

    class Sqr() : UnaryOperation(){
        override fun equal(a: BigDecimal): BigDecimal = a.pow(2)
        override fun toString(): String {
            val sqr = 178.toChar()
            return "${a!!.toDisplayString()}$sqr = $result"
        }
    }

    class Sqrt() : UnaryOperation(){
        override fun equal(a: BigDecimal): BigDecimal = sqrt(a.toDouble()).toBigDecimal()
        override fun toString(): String {
            return "√${a!!.toDisplayString()} = $result"
        }
    }

    class Sin() : UnaryOperation(){
        override fun equal(a: BigDecimal): BigDecimal = sin(a.toDouble() * Math.PI / 180).toBigDecimal()
        override fun toString(): String {
            return "sin(${a!!.toDisplayString()}) = $result"
        }
    }

    class Cos() : UnaryOperation(){
        override fun equal(a: BigDecimal): BigDecimal = cos(a.toDouble() * Math.PI / 180).toBigDecimal()
        override fun toString(): String {
            return "cos(${a!!.toDisplayString()}) = $result"
        }
    }

    class Tan() : UnaryOperation(){
        override fun equal(a: BigDecimal): BigDecimal = tan(a.toDouble() * Math.PI / 180).toBigDecimal()
        override fun toString(): String {
            return "tan(${a!!.toDisplayString()}) = $result"
        }
    }
}

