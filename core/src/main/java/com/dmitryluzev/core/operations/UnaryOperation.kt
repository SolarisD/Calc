package com.dmitryluzev.core.operations

abstract class UnaryOperation(var a: Double?): Operation {

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