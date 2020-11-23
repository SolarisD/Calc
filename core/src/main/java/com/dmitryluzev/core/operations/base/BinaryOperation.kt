package com.dmitryluzev.core.operations.base

import com.dmitryluzev.core.operations.OperationFactory
import com.dmitryluzev.core.values.Value

abstract class BinaryOperation(override var a: Value?, var b: Value?): Operation {
    protected abstract fun equal(a: Value, b: Value): Value
    override val result: Value?
        get() =
            if (a != null && b != null){
                try {
                    equal(a!!, b!!)
                }catch (e: Exception){
                    Value.NaN
                }
            } else {
                null
            }
}