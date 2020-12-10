package com.dmitryluzev.core.operations

import com.dmitryluzev.core.buffer.Converter

class Add internal constructor(a: Double? = null, b: Double? = null) : BinaryOperation(a, b){
    override fun result(): Double? {
        a?.let { _a ->
            b?.let { _b ->
                return _a + _b
            }
        }
        return null
    }

    override fun repeat(): Operation {
        return Add(result(), b)
    }
}

class Subtract internal constructor(a: Double? = null, b: Double? = null) : BinaryOperation(a, b){
    override fun result(): Double? {
        a?.let { _a ->
            b?.let { _b ->
                return _a - _b
            }
        }
        return null
    }

    override fun repeat(): Operation {
        return Subtract(result(), b)
    }
}

class Multiply internal constructor(a: Double? = null, b: Double? = null) : BinaryOperation(a, b){
    override fun result(): Double? {
        a?.let { _a ->
            b?.let { _b ->
                return _a * _b
            }
        }
        return null
    }

    override fun repeat(): Operation {
        return Multiply(result(), b)
    }
}

class Divide internal constructor(a: Double? = null, b: Double? = null) : BinaryOperation(a, b){
    override fun result(): Double? {
        a?.let { _a ->
            b?.let { _b ->
                return _a / _b
            }
        }
        return null
    }

    override fun repeat(): Operation {
        return Divide(result(), b)
    }
}

class Percent internal constructor(a: Double? = null, b: Double? = null) : BinaryOperation(a, b){
    override fun result(): Double? {
        a?.let { _a ->
            b?.let { _b ->
                return _a * _b / 100.0
            }
        }
        return null
    }

    override fun repeat(): Operation {
        return Percent(result(), b)
    }
}

object OperationFactory{
    const val ADD_ID = "ADD_OPERATION"
    const val SUBTRACT_ID = "SUBTRACT_OPERATION"
    const val MULTIPLY_ID = "MULTIPLY_OPERATION"
    const val DIVIDE_ID = "DIVIDE_OPERATION"
    const val PERCENT_ID = "PERCENT_OPERATION"

    fun create(id: String, a: Double? = null, b: Double? = null): Operation? = when(id){
        ADD_ID -> Add(a, b)
        SUBTRACT_ID -> Subtract(a, b)
        MULTIPLY_ID -> Multiply(a, b)
        DIVIDE_ID -> Divide(a, b)
        PERCENT_ID -> Percent(a, b)
        else -> null
    }
    fun getId(operation: Operation): String = when(operation){
        is Add -> ADD_ID
        is Subtract -> SUBTRACT_ID
        is Multiply -> MULTIPLY_ID
        is Divide -> DIVIDE_ID
        is Percent -> PERCENT_ID
        else -> throw IllegalArgumentException("Unknown class")
    }

    fun toStoreString(operation: Operation?) = when(operation){
        is UnaryOperation -> {
            "${getId(operation)};${Converter.doubleToString(operation.a)}"
        }
        is BinaryOperation -> {
            "${getId(operation)};${Converter.doubleToString(operation.a)};${Converter.doubleToString(operation.b)}"
        }
        else -> null
    }
    fun fromStoreString(string: String?): Operation?{
        string?.let {
            val list = it.split(';')
            var a: Double? = null
            if (list.size > 1 && list[1] != "null") a = Converter.stringToDouble(list[1])
            var b: Double? = null
            if (list.size > 2 && list[2] != "null") b = Converter.stringToDouble(list[2])
            return create(list[0], a, b)
        }
        return null
    }
}