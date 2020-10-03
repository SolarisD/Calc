package com.solarisd.calc.core

import java.text.DecimalFormatSymbols
import kotlin.math.pow

data class Value(private var s: Boolean = false, private var m: String = "", private var e: Int? = null, private var nan: Double? = null) {
    companion object{
        private const val maxLength = 10
        private const val base = 10.0
        private val ds = DecimalFormatSymbols.getInstance().decimalSeparator

        fun fromDouble(value: Double): Value{
            if (value.isFinite()) {
                val fmt = "%.${maxLength}E"
                var scf = String.format(fmt, value)
                //sign
                val s = scf[0] == '-'
                if (s) scf = scf.drop(1)
                //mantissa

                var m = scf.substringBefore('E').replace(ds.toString(), "")
                var tmp = m.length
                for (i in (m.length - 1) downTo 0) {
                    if (m[i] == '0') tmp = i
                    else break
                }
                if (tmp < m.length) m = m.substring(0 until tmp)
                if(m.isEmpty()) return Value()
                //exponent
                val strExponent = scf.substringAfter('E')
                val expSign = strExponent[0] == '-'
                var e = if (expSign) -strExponent.drop(1).toInt()
                else strExponent.drop(1).toInt()
                e = e - m.length + 1
                //move dot
                if (e > 0 && (m.length + e) <= maxLength){
                    m = (m.toLong() * base.pow(e).toLong()).toString()
                    e = 0
                }

                return Value(s, m, if (e != 0) e else null)
            }
            return Value(nan = value)
        }
        fun fromString(value: String?): Value{
            try {
                val dbl = value?.replace(" ", "")?.toDouble()
                if (dbl != null) return fromDouble(dbl)
            }catch(e: Exception){
                return Value()
            }
            return Value()
        }
    }
    override fun toString(): String {
        nan?.let{
            return it.toString()
        }
        e?.let {
            if (it == 0){
                return sign() + addDelimiters(m, ' ') + ds
            } else if (it + m.length > maxLength || it < -maxLength){
                return sign() + scfString()
            } else if (m.isEmpty() && it < 0){
                val stb = StringBuilder("0$ds")
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
                return sign() + stb.toString()
            }
        }
        return if(m.isNotEmpty()) sign() + addDelimiters(m, ' ')
        else sign() + "0"
    }
    fun toDouble(): Double{
        nan?.let{
            return it
        }
        val lm = if (m.isNotEmpty()) m.toLong() else 0L
        if (s) return -lm * base.pow(e ?: 0)
        return lm * base.pow(e ?: 0)
    }
    fun clear(){
        s = false
        m = ""
        e = null
        nan = null
    }
    fun negative(){
        s = !s
    }
    fun backspace(){
        nan?.let { clear() }
        e?.let {
            if (it > 0) {
                e = it - 1
                if (e == 0) e = null
                e?.let {
                    if (it > 0 && (m.length + it) <= maxLength){
                        val s = m.toLong() * base.pow(it).toLong()
                        m = s.toString()
                        e = null
                    }
                }
            }
            if (it < 0) e = it + 1
            if (it == 0) e = null
            if (it + m.length >= maxLength) {
                return
            }
        }
        if (m.isNotEmpty()) m = m.dropLast(1)
    }
    fun addFractional(){
        nan?.let { clear() }
        if (m.length >= maxLength) return
        if (e == null) e = 0
    }
    fun addNumber(num: Char){
        nan?.let { clear() }
        if (m.length >= maxLength) return
        if (num == '0'){
            if (e == null && m.isEmpty()) return
            else if (m.isEmpty()) e = e!! - 1
            else {
                m += num
                if (e != null) e = e!! - 1
            }
        }else{
            m += num
            if (e != null) e = e!! - 1
        }
    }
    private fun addDelimiters(value: String, gs: Char): String{
        val stb = StringBuilder()
        for (i in value.indices){
            stb.append(value[value.length-1-i])
            if (i != value.length-1 && (i+1) % 3 == 0) stb.append(gs)

        }
        stb.reverse()
        return stb.toString()
    }
    private fun scfString(): String{
        val s = when(m.length){
            0->{"0{$ds}0"}
            1->{"$m{$ds}0"}
            else->{StringBuilder(m).insert(1, ds).toString()}
        }
        val eAdd = m.length - 1
        val e = if (e != null) e!! + eAdd
        else eAdd
        val ins2 = if (e > 0) "E+${e}"
        else "E${e}"
        return  s + ins2
    }
    private fun sign(): String {
        return if (s) "-"
        else ""
    }
}