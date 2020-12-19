package com.dmitryluzev.testapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dmitryluzev.core.operations.Operation
import java.util.*

@Entity(tableName = "history_records")
data class Record(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var date: Date,
    var op: Operation
)
