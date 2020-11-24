package com.dmitryluzev.core.operations

import com.dmitryluzev.core.values.Value

interface Operation {
    val result: Value?
}

abstract class UnaryOperation(var a: Value?): Operation

abstract class BinaryOperation(var a: Value?, var b: Value?, var percentage: Boolean = false): Operation