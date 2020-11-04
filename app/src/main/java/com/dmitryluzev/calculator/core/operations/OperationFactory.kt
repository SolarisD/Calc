package com.dmitryluzev.calculator.core.operations

import com.dmitryluzev.calculator.core.operations.base.Operation

object OperationFactory {
    enum class ID(val tag: String) {
        ADD("ADD_OPERATION"),
        SUBTRACT("SUBTRACT_OPERATION"),
        MULTIPLY("MULTIPLY_OPERATION"),
        DIVIDE("DIVIDE_OPERATION")
    }
    fun createFromID(id: ID): Operation = when(id){
        ID.ADD -> Add()
        ID.SUBTRACT -> Subtract()
        ID.MULTIPLY -> Multiply()
        ID.DIVIDE -> Divide()
    }
    fun createFromIdTag(tag: String): Operation? = when(tag){
        ID.ADD.tag -> Add()
        ID.SUBTRACT.tag -> Subtract()
        ID.MULTIPLY.tag -> Multiply()
        ID.DIVIDE.tag -> Subtract()
        else -> null
    }
    fun getTagId(operation: Operation): String = when(operation){
        is Add -> ID.ADD.tag
        is Subtract -> ID.SUBTRACT.tag
        is Multiply -> ID.MULTIPLY.tag
        is Divide -> ID.DIVIDE.tag
        else -> throw IllegalArgumentException("operation isn't Operation class")
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