package com.dmitryluzev.calculator.core.operations

object Companion {
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
}