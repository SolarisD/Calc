package com.dmitryluzev.calculator.operations

import com.dmitryluzev.calculator.core.Value
import java.lang.Exception

abstract class UnaryOperation(): Operation {
    override var a: Value? = null
    override val isComplete: Boolean
        get() = a != null
    protected abstract fun equal(a: Value): Value
    override val result: Value?
        get() =
            if (isComplete){
                try {
                    equal(a!!)
                }catch (e: Exception){
                    Value.NaN
                }

            } else {
                null
            }
}