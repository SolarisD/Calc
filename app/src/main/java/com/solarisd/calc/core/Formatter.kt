package com.solarisd.calc.core

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

object Formatter{
    private val s = DecimalFormatSymbols(Locale.US)
    private val df = DecimalFormat()
    init {
        s.decimalSeparator = '.'
        s.groupingSeparator = ' '
        df.maximumFractionDigits = 50
        df.decimalFormatSymbols = s
        df.isGroupingUsed = true
    }
    fun toString(bigDecimal: BigDecimal): String = df.format(bigDecimal)
    fun toBigDecimal(string: String): BigDecimal = string.replace(" ", "").toBigDecimal()

}

fun BigDecimal.toDisplayString(): String = Formatter.toString(this)
fun String.fromDisplayString(): BigDecimal = Formatter.toBigDecimal(this)