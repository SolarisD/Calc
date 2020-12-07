package com.dmitryluzev.core.values

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dmitryluzev.core.Symbols
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import kotlin.math.pow

class BufferDouble {
    companion object {
        private const val maxLength = 10
        private const val base = 10.0
        private val ds = DecimalFormatSymbols.getInstance().decimalSeparator
        private val df = NumberFormat.getInstance()
    }

    private val _out = MutableLiveData<Double>()
    val out: LiveData<Double>
        get() = _out

    private var s: Boolean = false
    private var m: String = ""
    private var e: Int? = null
    private var u: Int? = null

    fun get(): Double {
        u?.let{
            return when(it){
                -1 -> {Double.NEGATIVE_INFINITY}
                1 -> {Double.POSITIVE_INFINITY}
                else ->{Double.NaN}
            }
        }
        val lm = if (m.isNotEmpty()) m.toLong() else 0L
        if (s) return -lm * Value.base.pow(e ?: 0)
        return lm * Value.base.pow(e ?: 0)
    }

    fun set(value: Double) {
        if (value.isFinite()) {
            val fmt = "%.${Value.maxLength - 1}E"
            var scf = String.format(fmt, value)
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
            if (e!! > 0 && (m.length + e!!) <= Value.maxLength){
                m = (m.toLong() * Value.base.pow(e!!).toLong()).toString()
                e = 0
            }
            if(e == 0) e = null
            return
        } else {
            clear()
            when(value){
                Double.NEGATIVE_INFINITY->{u = -1}
                Double.POSITIVE_INFINITY->{u = 1}
                else->{u = 0}
            }
        }
        _out.value = get()
    }

    fun clear() {
        s = false
        m = ""
        e = null
        u = null
        _out.value = get()
    }

    fun negative(){
        s = !s
        _out.value = get()
    }

    fun backspace(){
        e?.let {
            if (it == 0) {
                e = null
                return
            }
            if (it > 0) {
                e = it - 1
                e?.let {
                    if (it > 0 && (m.length + it) <= maxLength){
                        val s = m.toLong() * base.pow(it).toLong()
                        m = s.toString()
                        e = null
                    }
                }
            }
            if (it < 0) e = it + 1
            if (it + m.length >= maxLength) {
                return
            }
        }
        if (m.isNotEmpty()) m = m.dropLast(1)
        _out.value = get()
    }

    fun symbol(symbol: Symbols){
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
        _out.value = get()
    }

    private fun addDot(){
        if (m.length >= maxLength) return
        if (e == null) e = 0
    }

    private fun addNumber(num: Char){
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
}