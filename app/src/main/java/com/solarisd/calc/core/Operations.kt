package com.solarisd.calc.core

import java.lang.Exception
import java.math.BigDecimal
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

interface RootOperation {
    var a: BigDecimal?
    val isInit: Boolean
    val result: String
}

abstract class UnaryOperation(): RootOperation{
    override var a: BigDecimal? = null
    override val isInit: Boolean
        get() = a != null
    protected abstract fun equal(a: BigDecimal): BigDecimal
    override val result: String
        get() = try {
            if (isInit){
                equal(a!!).toString()
            } else {
                "Operation isn't initialized"
            }
        } catch (e: Exception){
            "Error"
        }
}
abstract class BinaryOperation(): RootOperation{
    override var a: BigDecimal? = null
    var b: BigDecimal? = null
    override val isInit: Boolean
        get() = a != null && b != null
    protected abstract fun equal(a: BigDecimal, b: BigDecimal): BigDecimal
    override val result: String
        get() = try {
            if (isInit){
                equal(a!!, b!!).toString()
            } else {
                "Operation isn't initialized"
            }
        } catch (e: Exception){
            "Error"
        }
}

class Operations{
    class Add() : BinaryOperation(){
        override fun equal(a: BigDecimal, b: BigDecimal): BigDecimal = a.add(b)
    }

    class Subtract() : BinaryOperation(){
        override fun equal(a: BigDecimal, b: BigDecimal): BigDecimal = a.subtract(b)
    }

    class Multiply() : BinaryOperation(){
        override fun equal(a: BigDecimal, b: BigDecimal): BigDecimal = a.multiply(b)
    }

    class Divide() : BinaryOperation(){
        override fun equal(a: BigDecimal, b: BigDecimal): BigDecimal = a.divide(b)
    }

    class Sqr() : UnaryOperation(){
        override fun equal(a: BigDecimal): BigDecimal = a.pow(2)
    }

    class Sqrt() : UnaryOperation(){
        override fun equal(a: BigDecimal): BigDecimal = sqrt(a.toDouble()).toBigDecimal()
    }

    class Sin() : UnaryOperation(){
        override fun equal(a: BigDecimal): BigDecimal = sin(a.toDouble() * Math.PI / 180).toBigDecimal()
    }

    class Cos() : UnaryOperation(){
        override fun equal(a: BigDecimal): BigDecimal = cos(a.toDouble() * Math.PI / 180).toBigDecimal()
    }

    class Tan() : UnaryOperation(){
        override fun equal(a: BigDecimal): BigDecimal = tan(a.toDouble() * Math.PI / 180).toBigDecimal()
    }
}

