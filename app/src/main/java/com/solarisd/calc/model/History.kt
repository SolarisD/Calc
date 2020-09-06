package com.solarisd.calc.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "history_records")
data class Record(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var op: String,
    var a: BigDecimal,
    var b: BigDecimal? = null,
    var result: BigDecimal)