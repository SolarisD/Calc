package com.dmitryluzev.calculator.core.operations

import com.dmitryluzev.calculator.core.Value

class Multiply internal constructor() : BinaryOperation() {
    override fun equal(a: Value, b: Value): Value = a * b
    override fun toString(): String {
        val ret = StringBuilder()
        a?.let { ret.append(it.toString()); ret.append(" × "); }
        b?.let { ret.append(it.toString()) }
        result?.let { ret.append(" = "); ret.append(result.toString()) }
        return ret.toString()
    }
}