package com.dmitryluzev.basic.operation

class Percent(a: Double? = null, b: Double? = null) : Operation(a, b) {

    override fun repeat(): Operation {
        return Percent(result(), b)
    }

    override fun result(): Double? {
        a?.let { _a ->
            b?.let { _b ->
                return _a * _b / 100.0
            }
        }
        return null
    }
}