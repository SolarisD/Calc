package com.solarisd.calc.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.solarisd.calc.core.enums.toOperators
import com.solarisd.calc.core.toDisplayString
import java.math.BigDecimal

@Entity(tableName = "history_records")
data class Record(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var op: String,
    var a: BigDecimal,
    var b: BigDecimal? = null,
    var result: BigDecimal){

    override fun toString(): String{
        op.toOperators()?.let {
            val res = if (it.unary) " = ${result.toDisplayString()}" else " ${b?.toDisplayString()} = ${result.toDisplayString()}"
            return if (it.unary) {
                "${it.symbol}(${a.toDisplayString()})$res"
            } else {
                "${a.toDisplayString()} ${it.symbol}$res"
            }
        }
        return ""
    }
}