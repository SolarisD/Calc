package com.dmitryluzev.core.operations.base

import com.dmitryluzev.core.operations.OperationFactory
import com.dmitryluzev.core.values.Value

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