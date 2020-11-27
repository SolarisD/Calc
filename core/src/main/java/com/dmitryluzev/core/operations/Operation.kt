package com.dmitryluzev.core.operations

import com.dmitryluzev.core.values.Value

interface Operation {
    override fun hashCode(): Int
    override fun equals(other: Any?): Boolean
    val result: Value?
}

abstract class UnaryOperation(var a: Value?): Operation{
    override fun hashCode(): Int {
        return a.hashCode()
    }

    override fun equals(other: Any?): Boolean = when(other) {
        is UnaryOperation -> {
            other::class.java == this::class.java && other.a == this.a
        }
        else -> false
    }
}

abstract class BinaryOperation(var a: Value?, var b: Value?, var percentage: Boolean = false): Operation{
    abstract val id: String
    override fun hashCode(): Int {
        return a.hashCode() + b.hashCode() + percentage.hashCode()
    }

    override fun equals(other: Any?): Boolean = when(other) {
        is BinaryOperation -> {
            other::class.java == this::class.java && other.a == this.a && other.b == this.b && other.percentage == this.percentage
        }
        else -> false
    }
}