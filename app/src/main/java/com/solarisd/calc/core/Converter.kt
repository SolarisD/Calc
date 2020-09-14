package com.solarisd.calc.core

import java.lang.StringBuilder
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

object Converter {
    private val s = DecimalFormatSymbols(Locale.US)
    private val f = DecimalFormat()
    init {
        s.groupingSeparator = ' '
        f.decimalFormatSymbols = s
        f.isGroupingUsed = true
    }
    fun doubleToDisplayedString(value: Double?): String =
        if (value == null) "null"
        else {
            if (value.isFinite()) {
                val strValue = value.toString()
                if (strValue.indexOf('.') == -1) {
                    f.format(value.toInt())
                } else {
                    val fractionalBuilder = StringBuilder(strValue.substringAfter('.'))
                    val count = fractionalBuilder.length - 1
                    //cleanup zeroes
                    for (i in count downTo 0) {
                        if (fractionalBuilder[i] == '0') {
                            fractionalBuilder.deleteAt(i)
                        } else break
                    }
                    if (fractionalBuilder.isEmpty()) {
                        f.format(strValue.substringBefore('.').toInt())
                    } else {
                        "${f.format(strValue.substringBefore('.').toInt())}.${fractionalBuilder}"
                    }
                }
            } else {
                value.toString()
            }
        }
}

fun Double?.toDisplayString(): String = Converter.doubleToDisplayedString(this)
fun String.toDoubleFromDisplay(): Double = this.replace(" ", "").toDouble()