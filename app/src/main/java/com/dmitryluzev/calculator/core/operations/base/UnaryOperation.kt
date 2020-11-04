package com.dmitryluzev.calculator.core.operations.base

import com.dmitryluzev.calculator.core.Value
import com.dmitryluzev.calculator.core.operations.OperationFactory
import java.lang.Exception

abstract class UnaryOperation(override var a: Value?): Operation {
    protected abstract fun equal(a: Value): Value
    override val result: Value?
        get() =
            if (a != null){
                try {
                    equal(a!!)
                }catch (e: Exception){
                    Value.NaN
                }
            } else {
                null
            }
    override fun toStoreString(): String = "${OperationFactory.getId(this)};${a}"
}