package com.dmitryluzev.calculator.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dmitryluzev.calculator.core.Operation
import com.dmitryluzev.calculator.core.Value

@Entity(tableName = "states")
data class State(
    @PrimaryKey
    val id: Int = 0,
    val buffer: Value? = null,
    val bufferClearRequest: Boolean = false,
    val memory: Value? = null,
    val binary: Operation? = null,
    val last: Operation? = null
)