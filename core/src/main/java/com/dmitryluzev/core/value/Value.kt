package com.dmitryluzev.core.value

import java.text.DecimalFormatSymbols
import kotlin.math.pow

open class Value{
    constructor()
    constructor(double: Double): this(){
        set(double)
    }
    protected var i: Boolean? = null
    protected var s: Boolean = false
    protected var m: String = ""
    protected var e: Int? = null

    companion object{
        const val maxLength = 10
        const val base = 10.0
        val ds = DecimalFormatSymbols.getInstance().decimalSeparator
    }
    fun get(): Double{
        i?.let{
            return if(it) {
                if (s) Double.NEGATIVE_INFINITY
                else Double.POSITIVE_INFINITY
            } else {
                Double.NaN
            }
        }
        val lm = if (m.isNotEmpty()) m.toLong() else 0L
        if (s) return -lm * base.pow(e ?: 0)
        return lm * base.pow(e ?: 0)
    }
    fun set(double: Double){
        if (double.isFinite()) {
            val fmt = "%.${maxLength - 1}E"
            var scf = String.format(fmt, double)
            //sign
            s = scf[0] == '-'
            if (s) scf = scf.drop(1)

            //mantissa
            m = scf.substringBefore('E').replace(ds.toString(), "")
            var tmp = m.length
            for (i in (m.length - 1) downTo 0) {
                if (m[i] == '0') tmp = i
                else break
            }
            if (tmp < m.length) m = m.substring(0 until tmp)
            if(m.isEmpty()) {
                return
            }
            //exponent
            val strExponent = scf.substringAfter('E')
            val expSign = strExponent[0] == '-'
            e = if (expSign) -strExponent.drop(1).toInt()
            else strExponent.drop(1).toInt()

            e = e!! - m.length + 1
            //move dot
            if (e!! > 0 && (m.length + e!!) <= maxLength){
                m = (m.toLong() * base.pow(e!!).toLong()).toString()
                e = 0
            }
            if(e == 0) e = null
        } else {
            when(double){
                Double.POSITIVE_INFINITY->{
                    i = true
                }
                Double.NEGATIVE_INFINITY->{
                    i = true
                    s = true
                }
                else->{
                    i = false
                }
            }
        }
    }
    override fun toString(): String{
        i?.let{
            return if(it) {
                if (s) Double.NEGATIVE_INFINITY.toString()
                else Double.POSITIVE_INFINITY.toString()
            } else {
                Double.NaN.toString()
            }
        }
        e?.let {
            if (it == 0){
                return sign(s) + addDelimiters( m,' ') + ds
            } else if (it + m.length > maxLength || it < -maxLength){
                return sign(s) + toScfString()
            } else if (m.isEmpty() && it < 0){
                val stb = StringBuilder("0${ds}")
                val end = -it
                for (i in 1..end){
                    stb.append('0')
                }
                return sign(s) + stb.toString()
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
                    else if (stb.length == -it) stb.insert(0, "0${ds}")
                    else {
                        val end = -it - stb.length
                        for (i in 1..end){
                            stb.insert(0,'0')
                        }
                        stb.insert(0, "0${ds}")
                    }

                }
                return sign(s) + stb.toString()
            }
        }
        return if(m.isNotEmpty()) sign(s) + addDelimiters(m, ' ')
        else sign(s) + "0"
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
    private fun toScfString(): String{
        val s = when(m.length){
            0->{"0${ds}0"}
            1->{"$m${ds}0"}
            else->{StringBuilder(m).insert(1, ds).toString()}
        }
        val eAdd = m.length - 1
        e = if (e != null) e!! + eAdd
        else eAdd
        val ins2 = if (e!! > 0) "E+${e}"
        else "E${e}"
        return  s + ins2
    }
}