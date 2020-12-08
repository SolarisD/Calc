package com.dmitryluzev.core

interface Operation {
    val tag: String
    override fun hashCode(): Int
    override fun equals(other: Any?): Boolean
    fun result(): Double?
    fun copy(): Operation
    fun repeat(): Operation
}

abstract class UnaryOperation(var a: Double?): Operation{
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

abstract class BinaryOperation(var a: Double?, var b: Double?, var percentage: Boolean = false): Operation{
    override fun hashCode(): Int {
        return a.hashCode() + b.hashCode() + percentage.hashCode()
    }

    override fun equals(other: Any?): Boolean = when(other) {
        is BinaryOperation -> {
            other::class.java == this::class.java && other.a == this.a && other.b == this.b && other.percentage == this.percentage
        }
        else -> false
    }

    override fun toString(): String {
        val ret = StringBuilder()
        a?.let {
            ret.append(Converter.doubleToString(it))
            ret.append(" ")
            ret.append(tag)
            ret.append(" ")
        }
        b?.let { ret.append(Converter.doubleToString(it)) }
        if (percentage){ ret.append("%") }
        result()?.let { ret.append(" = "); ret.append(Converter.doubleToString(it)) }
        return ret.toString()
    }
}

class Add internal constructor(a: Double? = null, b: Double? = null, percentage: Boolean = false) : BinaryOperation(a, b, percentage){
    override val tag: String
        get() = "+"

    override fun result(): Double? {
        a?.let { _a ->
            b?.let { _b ->
                return if (percentage) _a * (1 + _b * 0.01) else _a + _b
            }
        }
        return null
    }

    override fun copy(): Operation {
        return Add(a, b, percentage)
    }

    override fun repeat(): Operation {
        return Add(result(), b, percentage)
    }
}

class Subtract internal constructor(a: Double? = null, b: Double? = null, percentage: Boolean = false) : BinaryOperation(a, b, percentage){
    override val tag: String
        get() = "-"

    override fun result(): Double? {
        a?.let { _a ->
            b?.let { _b ->
                return if (percentage) _a * (1 - _b * 0.01) else _a - _b
            }
        }
        return null
    }

    override fun copy(): Operation {
        return Subtract(a, b, percentage)
    }

    override fun repeat(): Operation {
        return Subtract(result(), b, percentage)
    }
}

class Multiply internal constructor(a: Double? = null, b: Double? = null, percentage: Boolean = false) : BinaryOperation(a, b, percentage){
    override val tag: String
        get() = "×"

    override fun result(): Double? {
        a?.let { _a ->
            b?.let { _b ->
                return if (percentage) _a * _a * _b * 0.01 else _a * _b
            }
        }
        return null
    }

    override fun copy(): Operation {
        return Multiply(a, b, percentage)
    }

    override fun repeat(): Operation {
        return Multiply(result(), b, percentage)
    }
}

class Divide internal constructor(a: Double? = null, b: Double? = null, percentage: Boolean = false) : BinaryOperation(a, b, percentage){
    override val tag: String
        get() = "÷"

    override fun result(): Double? {
        a?.let { _a ->
            b?.let { _b ->
                return if (percentage) 100 / _b else _a / _b
            }
        }
        return null
    }

    override fun copy(): Operation {
        return Divide(a, b, percentage)
    }

    override fun repeat(): Operation {
        return Divide(result(), b, percentage)
    }
}


object OperationFactory{
    const val ADD_ID = "ADD_OPERATION"
    const val SUBTRACT_ID = "SUBTRACT_OPERATION"
    const val MULTIPLY_ID = "MULTIPLY_OPERATION"
    const val DIVIDE_ID = "DIVIDE_OPERATION"

    fun create(id: String, a: Double? = null, b: Double? = null, percentage: Boolean = false): Operation? = when(id){
        ADD_ID -> Add(a, b, percentage)
        SUBTRACT_ID -> Subtract(a, b, percentage)
        MULTIPLY_ID -> Multiply(a, b, percentage)
        DIVIDE_ID -> Divide(a, b, percentage)
        else -> null
    }
    fun getId(operation: Operation): String = when(operation){
        is Add -> ADD_ID
        is Subtract -> SUBTRACT_ID
        is Multiply -> MULTIPLY_ID
        is Divide -> DIVIDE_ID
        else -> throw IllegalArgumentException("Unknown class")
    }

    fun toStoreString(operation: Operation?) = when(operation){
        is UnaryOperation -> {
            "${getId(operation)};${Converter.doubleToString(operation.a)}"
        }
        is BinaryOperation -> {
            "${getId(operation)};${Converter.doubleToString(operation.a)};${Converter.doubleToString(operation.b)};${operation.percentage}"
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
            var percentage = false
            if (list.size > 3 && list[3] != "null") percentage = list[3].toBoolean()
            return create(list[0], a, b, percentage)
        }
        return null
    }
}

