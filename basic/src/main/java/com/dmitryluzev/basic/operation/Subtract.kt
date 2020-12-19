package com.dmitryluzev.basic.operation

class Subtract(a: Double? = null, b: Double? = null) : Operation(a, b) {

    override fun repeat(): Operation {
        return Subtract(result(), b)
    }

    override fun result(): Double? {
        a?.let { _a ->
            b?.let { _b ->
                return _a - _b
            }
        }
        return null
    }
}