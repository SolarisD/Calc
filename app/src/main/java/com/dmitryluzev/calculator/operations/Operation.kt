package com.dmitryluzev.calculator.operations

import com.dmitryluzev.calculator.core.Value

interface Operation {
    val id: String
    var a: Value?
    val isComplete: Boolean
    val result: Value?
    override fun toString(): String
    fun toStoreString(): String
}