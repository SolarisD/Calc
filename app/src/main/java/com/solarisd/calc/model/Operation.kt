package com.solarisd.calc.model

import com.solarisd.calc.core.enums.Operators
import com.solarisd.calc.unit.toDisplayString
import java.math.BigDecimal


data class Operation(
    val op: Operators,
    val a: BigDecimal,
    val b: BigDecimal? = null,
    val result: BigDecimal? = null)
{
    override fun toString(): String {
        val res = if (result != null){
            if (op.unary) " = ${result.toDisplayString()}" else " ${b?.toDisplayString()} = ${result.toDisplayString()}"
        } else ""

        return if (op.unary) {
            "${op.print}(${a.toDisplayString()})$res"
        } else {
            "${a.toDisplayString()} ${op.print}$res"
        }
    }
}