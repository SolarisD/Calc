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
        f.maximumFractionDigits = 12
        f.maximumIntegerDigits = 12
    }
}
fun StringBuilder.toDisplayString(): String {
    if (this.isNotBlank() && this.isNotEmpty()) return Converter.f.format(this.toString().toLong())
    return "0"
}
fun Long.toDisplayString(): String = Converter.f.format(this)
fun Double?.toDisplayString(): String? = Converter.f.format(this)
fun Double.toDisplayString(): String = Converter.f.format(this)
fun String.toDoubleFromDisplay(): Double = this.replace(" ", "").toDouble()