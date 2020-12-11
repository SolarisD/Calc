package com.dmitryluzev.core.buffer

import java.text.DecimalFormatSymbols
import kotlin.math.pow

object Converter {
    const val maxLength = 10
    const val base = 10.0
    val ds = DecimalFormatSymbols.getInstance().decimalSeparator

    fun stringToDouble(string: String?): Double?{
        string?.let {
            val str = string.replace(" ", "")
            return str.toDoubleOrNull()
        }
        return null
    }

    fun doubleToString(double: Double?): String?{
        double?.let {
            return bufferValueToString(doubleToBufferValue(double))
        }
        return null
    }

    fun bufferValueToDouble(value: Value): Double{
        value.u?.let{
            return when(it){
                -1 -> {Double.NEGATIVE_INFINITY}
                1 -> {Double.POSITIVE_INFINITY}
                else ->{Double.NaN}
            }
        }
        val lm = if (value.m.isNotEmpty()) value.m.toLong() else 0L
        if (value.s) return -lm * base.pow(value.e ?: 0)
        return lm * base.pow(value.e ?: 0)
    }

    fun bufferValueToString(value: Value): String{
        value.u?.let{
            return when(it){
                -1 -> {Double.NEGATIVE_INFINITY.toString()}
                1 -> {Double.POSITIVE_INFINITY.toString()}
                else -> {Double.NaN.toString()}
            }
        }
        value.e?.let {
            if (it == 0){
                return sign(value.s) + addDelimiters(value.m, ' ') + ds
            } else if (it + value.m.length > maxLength || it < -maxLength){
                return sign(value.s) + meToScfString(value.m, value.e)
            } else if (value.m.isEmpty() && it < 0){
                val stb = StringBuilder("0$ds")
                val end = -it
                for (i in 1..end){
                    stb.append('0')
                }
                return sign(value.s) + stb.toString()
            } else {
                val stb = StringBuilder(value.m)
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
                        stb.append(addDelimiters(integer, ' '))
                        stb.append(ds)
                        stb.append(fract)
                    }
                    else if (stb.length == -it) stb.insert(0, "0$ds")
                    else {
                        val end = -it - stb.length
                        for (i in 1..end){
                            stb.insert(0,'0')
                        }
                        stb.insert(0, "0$ds")
                    }

                }
                return sign(value.s) + stb.toString()
            }
        }
        return if(value.m.isNotEmpty()) sign(value.s) + addDelimiters(value.m, ' ')
        else sign(value.s) + "0"
    }

    fun doubleToBufferValue(double: Double): Value{
        val value = Value()
        if (double.isFinite()) {
            val fmt = "%.${maxLength - 1}E"
            var scf = String.format(fmt, double)
            //sign
            value.s = scf[0] == '-'
            if (value.s) scf = scf.drop(1)

            //mantissa
            value.m = scf.substringBefore('E').replace(ds.toString(), "")
            var tmp = value.m.length
            for (i in (value.m.length - 1) downTo 0) {
                if (value.m[i] == '0') tmp = i
                else break
            }
            if (tmp < value.m.length) value.m = value.m.substring(0 until tmp)
            if(value.m.isEmpty()) {
                return Value()
            }
            //exponent
            val strExponent = scf.substringAfter('E')
            val expSign = strExponent[0] == '-'
            value.e = if (expSign) -strExponent.drop(1).toInt()
            else strExponent.drop(1).toInt()

            value.e = value.e!! - value.m.length + 1
            //move dot
            if (value.e!! > 0 && (value.m.length + value.e!!) <= maxLength){
                value.m = (value.m.toLong() * base.pow(value.e!!).toLong()).toString()
                value.e = 0
            }
            if(value.e == 0) value.e = null
        } else {
            when(double){
                Double.NEGATIVE_INFINITY->{value.u = -1}
                Double.POSITIVE_INFINITY->{value.u = 1}
                else->{value.u = 0}
            }
        }
        return value
    }

    private fun sign(s: Boolean) = if (s) "-" else ""

    private fun addDelimiters(string: String, gs: Char): String{
        val stb = StringBuilder()
        for (i in string.indices){
            stb.append(string[string.length-1-i])
            if (i != string.length-1 && (i+1) % 3 == 0) stb.append(gs)
        }
        stb.reverse()
        return if(stb.isNotEmpty()) stb.toString()
        else "0"
    }

    private fun meToScfString(m: String, e: Int?): String{
        val s = when(m.length){
            0->{"0${ds}0"}
            1->{"$m${ds}0"}
            else->{StringBuilder(m).insert(1, ds).toString()}
        }
        val eAdd = m.length - 1
        val e = if (e != null) e + eAdd
        else eAdd
        val ins2 = if (e > 0) "E+${e}"
        else "E${e}"
        return  s + ins2
    }
}