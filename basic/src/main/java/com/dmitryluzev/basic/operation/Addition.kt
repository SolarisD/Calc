package com.dmitryluzev.basic.operation

class Addition(a: Double? = null, b: Double? = null) : Operation(a, b) {

    override fun repeat(): Operation {
        return Addition(result(), b)
    }

    override fun result(): Double? {
        a?.let { _a ->
            b?.let { _b ->
                return _a + _b
            }
        }
        return null
    }
}