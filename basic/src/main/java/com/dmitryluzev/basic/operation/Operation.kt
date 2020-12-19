package com.dmitryluzev.basic.operation

abstract class Operation(var a: Double?, var b: Double?) {
    override fun hashCode(): Int {
        return a.hashCode() + b.hashCode() + this::class.java.hashCode()
    }

    override fun equals(other: Any?): Boolean = when(other) {
        is Operation -> {
            other::class.java == this::class.java && other.a == this.a && other.b == this.b
        }
        else -> false
    }
    abstract fun repeat(): Operation
    abstract fun result(): Double?

}
