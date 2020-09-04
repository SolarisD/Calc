package com.solarisd.calc.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.solarisd.calc.core.enums.Operators
import java.math.BigDecimal

@Entity(tableName = "operation_history")
data class OpHistory(
    @PrimaryKey(autoGenerate = true) var id: Int,
    val op: Operators,
    val a: BigDecimal,
    val b: BigDecimal? = null,
    val result: BigDecimal? = null)
{
    /*override fun toString(): String {
        val res = if (result != null){
            if (op.unary) " = $result" else " $b = $result"
        } else ""

        return if (op.unary) {
            "${op.print}($a)$res"
        } else {
            "$a ${op.print}$res"
        }
    }*/
}