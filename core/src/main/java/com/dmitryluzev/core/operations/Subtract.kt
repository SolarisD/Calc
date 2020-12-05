package com.dmitryluzev.core.operations

import com.dmitryluzev.core.values.Value


class Subtract internal constructor(a: Value? = null, b: Value? = null, percentage: Boolean = false) : BinaryOperation(a, b, percentage){
    override val id: String
        get() = "-"
    override val result: Value?
        get() =
            if (a != null && b != null){
                if (percentage){
                    a!! * (Value.getInstance("1.0")!! - b!! * Value.getInstance("0.01")!!)
                } else {
                    a!! - b!!
                }
            } else {
                null
            }
    override fun toString(): String {
        val ret = StringBuilder()
        a?.let { ret.append(it.toString()); ret.append(" - ") }
        b?.let { ret.append(it.toString()) }
        if (percentage){ ret.append("%") }
        result?.let { ret.append(" = "); ret.append(result.toString()) }
        return ret.toString()
    }
}