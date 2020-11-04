package com.dmitryluzev.calculator.core.operations.base

import com.dmitryluzev.calculator.core.Value
import com.dmitryluzev.calculator.core.operations.OperationFactory
import java.lang.Exception

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

    override fun toStoreString(): String = "${OperationFactory.getId(this)};${a};${b}"
}