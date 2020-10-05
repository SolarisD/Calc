package com.solarisd.calc.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.solarisd.calc.core.Operation

@Entity(tableName = "history_records")
data class Record(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var op: Operation
)
