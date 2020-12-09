package com.dmitryluzev.core.buffer

import com.dmitryluzev.core.Converter
import kotlin.math.abs
import kotlin.math.pow

class BufferImpl: Buffer {

    private var s: Boolean = false
    private var m: String = ""
    private var e: Int? = null
    private var u: Int? = null

    override fun get(): Double {
        u?.let{
            return when(it){
                -1 -> {Double.NEGATIVE_INFINITY}
                1 -> {Double.POSITIVE_INFINITY}
                else ->{Double.NaN}
            }
        }
        val lm = if (m.isNotEmpty()) m.toLong() else 0L
        if (s) return -lm * Converter.base.pow(e ?: 0)
        return lm * Converter.base.pow(e ?: 0)
    }

    override fun set(double: Double){
        if (double.isFinite()) {
            val fmt = "%.${Converter.maxLength - 1}E"
            var scf = String.format(fmt, double)
            //sign
            s = scf[0] == '-'
            if (s) scf = scf.drop(1)
            //mantissa
            m = scf.substringBefore('E').replace(Converter.ds.toString(), "")
            var tmp = m.length
            for (i in (m.length - 1) downTo 0) {
                if (m[i] == '0') tmp = i
                else break
            }
            if (tmp < m.length) m = m.substring(0 until tmp)
            if(m.isEmpty()) {
                clear()
                return
            }
            //exponent
            val strExponent = scf.substringAfter('E')
            val expSign = strExponent[0] == '-'
            e = if (expSign) -strExponent.drop(1).toInt()
            else strExponent.drop(1).toInt()
            e = e!! - m.length + 1
            //move dot
            if (e!! > 0 && (m.length + e!!) <= Converter.maxLength){
                m = (m.toLong() * Converter.base.pow(e!!).toLong()).toString()
                e = 0
            }
            if(e == 0) e = null
            return
        } else {
            clear()
            when(double){
                Double.NEGATIVE_INFINITY->{u = -1}
                Double.POSITIVE_INFINITY->{u = 1}
                else->{u = 0}
            }
        }
    }

    override fun clear() {
        s = false
        m = ""
        e = null
        u = null
    }

    override fun negative(){
        s = !s
    }

    override fun backspace(){
        e?.let {
            if (it == 0) {
                e = null
                return
            }
            if (it > 0) {
                e = it - 1
                e?.let {
                    if (it > 0 && (m.length + it) <= Converter.maxLength){
                        val s = m.toLong() * Converter.base.pow(it).toLong()
                        m = s.toString()
                        e = null
                    }
                }
            }
            if (it < 0) e = it + 1
            if (it + m.length >= Converter.maxLength) {
                return
            }
        }
        if (m.isNotEmpty()) m = m.dropLast(1)
    }

    override fun symbol(symbol: Symbols){
        when(symbol){
            Symbols.ZERO -> addNumber('0')
            Symbols.ONE -> addNumber('1')
            Symbols.TWO -> addNumber('2')
            Symbols.THREE -> addNumber('3')
            Symbols.FOUR -> addNumber('4')
            Symbols.FIVE -> addNumber('5')
            Symbols.SIX -> addNumber('6')
            Symbols.SEVEN -> addNumber('7')
            Symbols.EIGHT -> addNumber('8')
            Symbols.NINE -> addNumber('9')
            Symbols.DOT -> addDot()
        }
    }

    private fun addDot(){
        if (m.length >= Converter.maxLength) return
        if (e == null) e = 0
    }

    private fun addNumber(num: Char){
        val full = m.length >= Converter.maxLength ||
                (e ?: 0 <= -Converter.maxLength)
        if (full) return
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

    override fun toString(): String {
        u?.let{
            return when(it){
                -1 -> {Double.NEGATIVE_INFINITY.toString()}
                1 -> {Double.POSITIVE_INFINITY.toString()}
                else -> {Double.NaN.toString()}
            }
        }
        e?.let {
            if (it == 0){
                return sign() + addDelimiters(m, ' ') + Converter.ds
            } else if (it + m.length > Converter.maxLength || it < -Converter.maxLength){
                return sign() + scfString()
            } else if (m.isEmpty() && it < 0){
                val stb = StringBuilder("0${Converter.ds}")
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
                        stb.append(Converter.ds)
                        stb.append(fract)
                    }
                    else if (stb.length == -it) stb.insert(0, "0${Converter.ds}")
                    else {
                        val end = -it - stb.length
                        for (i in 1..end){
                            stb.insert(0,'0')
                        }
                        stb.insert(0, "0${Converter.ds}")
                    }

                }
                return sign() + stb.toString()
            }
        }
        return if(m.isNotEmpty()) sign() + addDelimiters(m, ' ')
        else sign() + "0"
    }

    private fun sign() = if (s) "-" else ""

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

    private fun scfString(): String{
        val s = when(m.length){
            0->{"0${Converter.ds}0"}
            1->{"$m${Converter.ds}0"}
            else->{StringBuilder(m).insert(1, Converter.ds).toString()}
        }
        val eAdd = m.length - 1
        val e = if (e != null) e!! + eAdd
        else eAdd
        val ins2 = if (e > 0) "E+${e}"
        else "E${e}"
        return  s + ins2
    }
}