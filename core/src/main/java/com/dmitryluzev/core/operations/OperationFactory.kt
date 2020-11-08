package com.dmitryluzev.core.operations

import com.dmitryluzev.core.operations.base.Operation
import com.dmitryluzev.core.values.Value

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
    fun getId(operation: Operation): String = when(operation){
        is Add -> ADD_ID
        is Subtract -> SUBTRACT_ID
        is Multiply -> MULTIPLY_ID
        is Divide -> DIVIDE_ID
        else -> throw IllegalArgumentException("Unknown class")
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
}