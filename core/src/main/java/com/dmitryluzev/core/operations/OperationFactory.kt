package com.dmitryluzev.core.operations

import com.dmitryluzev.core.operations.base.BinaryOperation
import com.dmitryluzev.core.operations.base.Operation
import com.dmitryluzev.core.operations.base.UnaryOperation
import com.dmitryluzev.core.values.Value
import com.dmitryluzev.core.values.toValue

object OperationFactory {
    const val ADD_ID = "ADD_OPERATION"
    const val SUBTRACT_ID = "SUBTRACT_OPERATION"
    const val MULTIPLY_ID = "MULTIPLY_OPERATION"
    const val DIVIDE_ID = "DIVIDE_OPERATION"
    fun create(id: String, a: Value? = null, b: Value? = null): Operation? = when(id){
        ADD_ID -> Add(a, b)
        SUBTRACT_ID -> Subtract(a, b)
        MULTIPLY_ID -> Multiply(a, b)
        DIVIDE_ID -> Divide(a, b)
        else -> null
    }
    fun copy(operation: Operation): Operation = when(operation) {
        is Add -> {
            val ret = Add()
            ret.a = operation.a
            ret.b = operation.b
            ret
        }
        is Subtract -> {
            val ret = Subtract()
            ret.a = operation.a
            ret.b = operation.b
            ret
        }
        is Multiply -> {
            val ret = Multiply()
            ret.a = operation.a
            ret.b = operation.b
            ret
        }
        is Divide -> {
            val ret = Divide()
            ret.a = operation.a
            ret.b = operation.b
            ret
        }
        else -> throw IllegalArgumentException("operation isn't Operation class")
    }
    private fun getId(operation: Operation): String = when(operation){
        is Add -> ADD_ID
        is Subtract -> SUBTRACT_ID
        is Multiply -> MULTIPLY_ID
        is Divide -> DIVIDE_ID
        else -> throw IllegalArgumentException("Unknown class")
    }
    fun toStoreString(operation: Operation?) = when(operation){
        is UnaryOperation -> {
            "${getId(operation)};${operation.a}"
        }
        is BinaryOperation -> {
            "${getId(operation)};${operation.a};${operation.b}"
        }
        else -> null
    }
    fun fromStoreString(string: String?): Operation?{
        string?.let {
            val list = it.split(';')
            var a: Value? = null
            if (list.size > 1 && list[1] != "null") a = list[1].toValue()
            var b: Value? = null
            if (list.size > 2 && list[2] != "null") b = list[2].toValue()
            return  OperationFactory.create(list[0], a, b)
        }
        return null
    }
}