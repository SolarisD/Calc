package com.dmitryluzev.core.operations

abstract class BinaryOperation(var a: Double?, var b: Double?): Operation {
    override fun hashCode(): Int {
        return a.hashCode() + b.hashCode()
    }

    override fun equals(other: Any?): Boolean = when(other) {
        is BinaryOperation -> {
            other::class.java == this::class.java && other.a == this.a && other.b == this.b
        }
        else -> false
    }
}



/*
override fun toString(): String {
    val ret = StringBuilder()
    a?.let {
        ret.append(Converter.doubleToString(it))
        ret.append(" ")
        ret.append(tag)
        ret.append(" ")
    }
    b?.let { ret.append(Converter.doubleToString(it)) }
    result()?.let { ret.append(" = "); ret.append(Converter.doubleToString(it)) }
    return ret.toString()
}*/
