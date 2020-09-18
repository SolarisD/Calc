package com.solarisd.calc.core

import java.lang.StringBuilder
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

object Converter {
    private val s = DecimalFormatSymbols(Locale.US)
    val f = DecimalFormat()
    init {
        s.groupingSeparator = ' '
        f.decimalFormatSymbols = s
        f.isGroupingUsed = true
    }
}
fun Double?.toDisplayString(): String?  = Converter.f.format(this)
fun Double.toDisplayString(): String = Converter.f.format(this)
fun String?.toDoubleFromDisplay(): Double? = this?.replace(" ", "")?.toDouble()
fun String.toDoubleFromDisplay(): Double = this.replace(" ", "").toDouble()