package com.dmitryluzev.core.operations

import com.dmitryluzev.core.values.Value

object OperationFactory {
    const val ADD_ID = "ADD_OPERATION"
    const val SUBTRACT_ID = "SUBTRACT_OPERATION"
    const val MULTIPLY_ID = "MULTIPLY_OPERATION"
    const val DIVIDE_ID = "DIVIDE_OPERATION"
    fun create(id: String, a: Value? = null, b: Value? = null, percentage: Boolean = false): Operation? = when(id){
        ADD_ID -> Add(a, b, percentage)
        SUBTRACT_ID -> Subtract(a, b, percentage)
        MULTIPLY_ID -> Multiply(a, b, percentage)
        DIVIDE_ID -> Divide(a, b, percentage)
        else -> null
    }
    private fun copy(operation: Operation): Operation = when(operation) {
        is Add -> {
            val ret = Add()
            ret.a = operation.a
            ret.b = operation.b
            ret.percentage = operation.percentage
            ret
        }
        is Subtract -> {
            val ret = Subtract()
            ret.a = operation.a
            ret.b = operation.b
            ret.percentage = operation.percentage
            ret
        }
        is Multiply -> {
            val ret = Multiply()
            ret.a = operation.a
            ret.b = operation.b
            ret.percentage = operation.percentage
            ret
        }
        is Divide -> {
            val ret = Divide()
            ret.a = operation.a
            ret.b = operation.b
            ret.percentage = operation.percentage
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
    fun change(oldOperation: Operation?, idNew: String): Operation? = when(oldOperation){
        is BinaryOperation -> {
            val newOp = create(idNew)
            if (newOp is BinaryOperation) {
                newOp.a = oldOperation.a
                newOp
            } else {
                oldOperation
            }
        }
        else -> oldOperation
    }
    fun repeat(operation: Operation?): Operation? = when(operation){
            is UnaryOperation -> {
                val newOp = copy(operation) as UnaryOperation
                newOp.a = operation.result
                newOp
            }
            is BinaryOperation -> {
                val newOp = copy(operation) as BinaryOperation
                newOp.a = operation.result
                newOp
            }
            else -> null
        }
    fun toStoreString(operation: Operation?) = when(operation){
        is UnaryOperation -> {
            "${getId(operation)};${operation.a}"
        }
        is BinaryOperation -> {
            "${getId(operation)};${operation.a};${operation.b};${operation.percentage}"
        }
        else -> null
    }
    fun fromStoreString(string: String?): Operation?{
        string?.let {
            val list = it.split(';')
            var a: Value? = null
            if (list.size > 1 && list[1] != "null") a = Value.getInstance(list[1])
            var b: Value? = null
            if (list.size > 2 && list[2] != "null") b = Value.getInstance(list[2])
            var percentage = false
            if (list.size > 3 && list[3] != "null") percentage = list[3].toBoolean()
            return create(list[0], a, b, percentage)
        }
        return null
    }
}