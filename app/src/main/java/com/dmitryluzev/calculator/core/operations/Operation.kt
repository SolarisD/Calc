package com.dmitryluzev.calculator.core.operations

import com.dmitryluzev.calculator.core.Value

interface Operation {
    var a: Value?
    val result: Value?
    fun toStoreString(): String
}