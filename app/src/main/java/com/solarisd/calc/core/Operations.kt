package com.solarisd.calc.core

import java.lang.Exception
import java.math.BigDecimal
import kotlin.math.*

interface Operation {
    var a: Double?
    val isComplete: Boolean
    val result: Double?
}
abstract class UnaryOperation(): Operation{
    override var a: Double? = null
    override val isComplete: Boolean
        get() = a != null
    protected abstract fun equal(a: Double): Double
    override val result: Double?
        get() =
            if (isComplete){
                try {
                    equal(a!!)
                }catch (e: Exception){
                    Double.NaN
                }

            } else {
                null
            }
}
abstract class BinaryOperation(): Operation{
    override var a: Double? = null
    var b: Double? = null
    override val isComplete: Boolean
        get() = a != null && b != null
    protected abstract fun equal(a: Double, b: Double): Double
    override val result: Double?
        get() =
            if (isComplete){
                try {
                    equal(a!!, b!!)
                }catch (e: Exception){
                    Double.NaN
                }

            } else {
                null
            }
}

class Operations{
    class Add() : BinaryOperation(){
        override fun equal(a: Double, b: Double): Double = a+b
        override fun toString(): String {
            val post = if (isComplete){
                "${b.toDisplayString()} = ${result.toDisplayString()}"
            } else {
                ""
            }
            return "${a.toDisplayString()} + $post"
        }
    }

    class Subtract() : BinaryOperation(){
        override fun equal(a: Double, b: Double): Double = a-b
        override fun toString(): String {
            val post = if (isComplete){
                "${b.toDisplayString()} = ${result.toDisplayString()}"
            } else {
                ""
            }
            return "${a.toDisplayString()} - $post"
        }
    }

    class Multiply() : BinaryOperation(){
        override fun equal(a: Double, b: Double): Double = a*b
        override fun toString(): String {
            val post = if (isComplete){
                "${b.toDisplayString()} = ${result.toDisplayString()}"
            } else {
                ""
            }
            return "${a.toDisplayString()} × $post"
        }
    }

    class Divide() : BinaryOperation(){
        override fun equal(a: Double, b: Double): Double = a/b
        override fun toString(): String {
            val post = if (isComplete){
                "${b.toDisplayString()} = ${result.toDisplayString()}"
            } else {
                ""
            }
            return "${a.toDisplayString()} ÷ $post"

        }
    }

    class Sqr() : UnaryOperation(){
        override fun equal(a: Double): Double = a*a
        override fun toString(): String {
            val sqr = 178.toChar()
            return "${a.toDisplayString()}$sqr = ${result.toDisplayString()}"
        }
    }

    class Sqrt() : UnaryOperation(){
        override fun equal(a: Double): Double = sqrt(a)
        override fun toString(): String {
            return "√${a.toDisplayString()} = ${result.toDisplayString()}"
        }
    }

    class Sin() : UnaryOperation(){
        override fun equal(a: Double): Double = sin(a * Math.PI / 180)
        override fun toString(): String {
            return "sin(${a.toDisplayString()}) = ${result.toDisplayString()}"
        }
    }

    class Cos() : UnaryOperation(){
        override fun equal(a: Double): Double = cos(a * Math.PI / 180)
        override fun toString(): String {
            return "cos(${a.toDisplayString()}) = ${result.toDisplayString()}"
        }
    }

    class Tan() : UnaryOperation(){
        override fun equal(a: Double): Double = tan(a * Math.PI / 180)
        override fun toString(): String {
            return "tan($${a.toDisplayString()}) = ${result.toDisplayString()}"
        }
    }
}

