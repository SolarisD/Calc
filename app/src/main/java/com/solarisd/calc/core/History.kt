package com.solarisd.calc.core

import com.solarisd.calc.core.enums.Operators
import java.math.BigDecimal


data class History(
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
            "${op.symbol}(${a.toDisplayString()})$res"
        } else {
            "${a.toDisplayString()} ${op.symbol}$res"
        }
    }
}