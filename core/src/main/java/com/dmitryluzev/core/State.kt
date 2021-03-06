package com.dmitryluzev.core

import com.dmitryluzev.core.operations.Operation

data class State(
    val buffer: Double? = null,
    val bufferString: String? = null,
    val memory: Double? = null,
    val operation: Operation? = null
)