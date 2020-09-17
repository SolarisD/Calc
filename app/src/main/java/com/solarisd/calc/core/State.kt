package com.solarisd.calc.core

data class State(
    val buffer: String? = null,
    val memory: String? = null,
    val binaryOperation: BinaryOperation? = null,
    val lastOperation: Operation? = null
)