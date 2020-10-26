package com.dmitryluzev.calculator.operations

import com.dmitryluzev.calculator.core.Value
import java.lang.Exception

abstract class BinaryOperation(): Operation {
    override var a: Value? = null
    var b: Value? = null
    override val isComplete: Boolean
        get() = a != null && b != null
    protected abstract fun equal(a: Value, b: Value): Value
    override val result: Value?
        get() =
            if (isComplete){
                try {
                    equal(a!!, b!!)
                }catch (e: Exception){
                    Value.NaN
                }

            } else {
                null
            }

    override fun toStoreString(): String = "${id};${a};${b}"
}