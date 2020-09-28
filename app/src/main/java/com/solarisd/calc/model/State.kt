package com.solarisd.calc.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.solarisd.calc.core.BinaryOperation
import com.solarisd.calc.core.Operation

@Entity(tableName = "states")
data class State(
    @PrimaryKey
    val id: Int = 0,
    val buffer: String? = null,
    val bufferClearRequest: Boolean = false,
    val memory: String? = null,
    val binary: Operation? = null,
    val last: Operation? = null
)