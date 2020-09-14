package com.solarisd.calc.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_records")
data class Record(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var expression: String)
