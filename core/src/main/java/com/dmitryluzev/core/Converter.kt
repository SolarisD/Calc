package com.dmitryluzev.core

import java.text.DecimalFormatSymbols
import java.text.NumberFormat

object Converter {
    const val maxLength = 10
    const val base = 10.0
    val ds = DecimalFormatSymbols.getInstance().decimalSeparator
    val df = NumberFormat.getInstance()

    fun stringToDouble(string: String?): Double?{
        string?.let {
            try {
                return df.parse(string.replace(" ", ""))?.toDouble() ?: 0.0
            }catch(e: Exception){
                return null
            }
        }
        return null
    }

    fun doubleToString(double: Double?): String?{
        double?.let {
            val value = Value()
            value.set(it)
            return value.toString()
        }
        return null
    }

    fun smeuToDouble(){

    }
}