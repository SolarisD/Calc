package com.dmitryluzev.calculator.core.operations

import com.dmitryluzev.calculator.core.Value
import java.lang.Exception

abstract class UnaryOperation(): Operation {
    override var a: Value? = null
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
    override fun toStoreString(): String = "${Companion.getTagId(this)};${a}"
}