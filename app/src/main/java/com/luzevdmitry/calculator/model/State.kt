package com.luzevdmitry.calculator.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.luzevdmitry.calculator.core.Operation
import com.luzevdmitry.calculator.core.Value

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