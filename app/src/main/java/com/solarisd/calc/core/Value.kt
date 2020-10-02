package com.solarisd.calc.core

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import kotlin.math.pow

data class Value(var s: Boolean = false, var m: String = "", var e: Int? = null) {
    companion object{
        const val maxLength = 10
        const val base = 10.0
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
    override fun toString(): String {
        e?.let {
            if (it == 0){
                return sign() + addDelimiters(m.toString()) + "."
            } else if (it + m.length > maxLength || it < -maxLength){
                return sign() + scfString()
            } else if (m.isEmpty() && it < 0){
                val stb = StringBuilder("0.")
                val end = -it
                for (i in 1..end){
                    stb.append('0')
                }
                return sign() + stb.toString()
            } else {
                val stb = StringBuilder(m)
                if (it > 0) {
                    val end = it
                    for (i in 1..end) {
                        stb.append('0')
                    }
                } else {
                    if (stb.length > -it) {
                        val integer = stb.substring(0, stb.length + it)
                        val fract = stb.substring(stb.length + it, stb.length)
                        stb.clear()
                        stb.append(addDelimiters(integer))
                        stb.append('.')
                        stb.append(fract)
                    }
                    else if (stb.length == -it) stb.insert(0, "0.")
                    else {
                        val end = -it - stb.length
                        for (i in 1..end){
                            stb.insert(0,'0')
                        }
                        stb.insert(0, "0.")
                    }

                }
                return sign() + stb.toString()
            }
        }
        return sign() + addDelimiters(m)
    }
    fun toDouble(): Double{
        val lm = if (m.isNotEmpty()) m.toString().toLong() else 0L
        if (s) return -lm * base.pow(e ?: 0)
        return lm * base.pow(e ?: 0)
    }
    fun clear(){
        s = false
        m = ""
        e = null
    }
    private fun addDelimiters(value: String): String{
        try {
            return f.format(value.toLong())
        } catch (e: Exception){
            return "0"
        }
    }
    private fun scfString(): String{
        val s = when(m.length){
            0->{"0.0"}
            1->{"$m.0"}
            else->{StringBuilder(m).insert(1, '.').toString()}
        }
        val eAdd = m.length - 1
        val e = if (e != null) e!! + eAdd
        else eAdd
        val ins2 = if (e > 0) "E+${e}"
        else "E${e}"
        return  s + ins2
    }
    private fun refactoring(){
        if (e == 0) e = null
        ////TODO FOR MINUS
        e?.let {
            if (it > 0 && (m.length + it) <= maxLength){
                val s = m.toLong() * base.pow(it).toLong()
                m = s.toString()
                e = null
            }
        }
    }
    private fun sign(): String {
        return if (s) "-"
        else ""
    }
}