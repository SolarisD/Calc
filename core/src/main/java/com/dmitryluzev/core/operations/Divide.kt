package com.dmitryluzev.core.operations

import com.dmitryluzev.core.values.Value

class Divide internal constructor(a: Value? = null, b: Value? = null, percentage: Boolean = false) : BinaryOperation(a, b, percentage){
    override val result: Value?
        get() =
            if (a != null && b != null){
                try {
                    if (percentage){
                        Value(100.0) / b!!
                    } else {
                        a!! / b!!
                    }
                }catch (e: Exception){
                    Value.NaN
                }
            } else {
                null
            }

    override fun toString(): String {
        val ret = StringBuilder()
        a?.let { ret.append(it.toString()); ret.append(" ÷ ");  }
        b?.let { ret.append(it.toString()) }
        if (percentage){ ret.append("%") }
        result?.let { ret.append(" = "); ret.append(result.toString()) }
        return ret.toString()
    }
}