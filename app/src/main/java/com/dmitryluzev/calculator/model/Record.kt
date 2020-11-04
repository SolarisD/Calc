package com.dmitryluzev.calculator.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dmitryluzev.calculator.core.operations.base.Operation

@Entity(tableName = "history_records")
data class Record(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var op: Operation
)
