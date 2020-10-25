package com.dmitryluzev.calculator.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dmitryluzev.calculator.core.Value
import com.dmitryluzev.calculator.operations.Operation

@Entity(tableName = "states")
data class State(
    @PrimaryKey
    val id: Int = 0,
    val buffer: Value? = null,
    val memory: Value? = null,
    val current: Operation? = null,
    val complete: Operation? = null
)