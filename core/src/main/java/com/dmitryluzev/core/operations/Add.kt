package com.dmitryluzev.core.operations

import com.dmitryluzev.core.values.Value


class Add internal constructor(a: Value? = null, b: Value? = null) : BinaryOperation(a, b) {
    override val result: Value?
        get() =
            if (a != null && b != null){
                try {
                    a!! + b!!
                }catch (e: Exception){
                    Value.NaN
                }
            } else {
                null
            }
    override fun toString(): String {
        val ret = StringBuilder()
        a?.let { ret.append(it.toString()); ret.append(" + ") }
        b?.let { ret.append(it.toString()) }
        result?.let { ret.append(" = "); ret.append(result.toString()) }
        return ret.toString()
    }
}