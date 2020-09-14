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
    fun doubleToString(value: Double?): String =
        if (value == null) "null"
        else {
            if (value.isFinite()) {
                val strValue = value.toString()
                if (strValue.indexOf('.') == -1) {
                    f.format(value.toInt())
                } else {
                    val builder = StringBuilder(strValue.substringAfter('.'))
                    val count = builder.length - 1
                    //cleanup zeroes
                    for (i in count downTo 0) {
                        if (builder[i] == '0') {
                            builder.deleteAt(i)
                        } else break
                    }
                    val fractional = builder.toString()
                    if (fractional.isEmpty()) {
                        f.format(strValue.substringBefore('.').toInt())
                    } else {
                        "${f.format(strValue.substringBefore('.').toInt())}.${fractional}"
                    }
                }
            } else {
                value.toString()
            }
        }
}

fun Double?.toDisplayString(): String = Converter.doubleToString(this)