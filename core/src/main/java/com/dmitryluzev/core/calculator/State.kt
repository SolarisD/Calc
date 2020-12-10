package com.dmitryluzev.core.calculator

import com.dmitryluzev.core.operations.Operation

data class State(
    val buffer: Double? = null,
    val memory: Double? = null,
    val operation: Operation? = null
)