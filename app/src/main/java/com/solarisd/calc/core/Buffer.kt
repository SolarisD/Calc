package com.solarisd.calc.core

import androidx.lifecycle.MutableLiveData
import kotlin.math.pow
import kotlin.text.StringBuilder

class Buffer() {
    companion object{
        const val maxLength = 10
        const val base = 10.0
    }
    val out: MutableLiveData<String> = MutableLiveData()

    private var minus: Boolean = false
    private val significant: StringBuilder = StringBuilder()
    private var exponent: Int? = null

    fun clear() {
        minus = false
        significant.clear()
        exponent = null
        out.postValue(toString())
    }
    fun getDouble(): Double{
        val s = if (significant.isNotEmpty()) significant.toString().toLong() else 0L
        if (minus) return -s * base.pow(exponent ?: 0)
        return s * base.pow(exponent ?: 0)
    }
    fun setDouble(value: Double){
        if (value.isFinite()) {
            val fmt = "%.${maxLength}E"
            var scf = String.format(fmt, value)
            //minus
            minus = scf[0] == '-'
            if (minus) scf = scf.drop(1)
            //significant
            var strSignificant = scf.substringBefore('E').replace(".", "")
            var tmp = strSignificant.length
            for (i in (strSignificant.length - 1) downTo 0) {
                if (strSignificant[i] == '0') tmp = i
                else break
            }
            if (tmp < strSignificant.length) strSignificant = strSignificant.substring(0 until tmp)
            significant.clear()
            significant.append(strSignificant)
            //exponent
            val strExponent = scf.substringAfter('E')
            val expSign = strExponent[0] == '-'
            exponent = if (expSign) -strExponent.drop(1).toInt()
            else strExponent.drop(1).toInt()
            exponent = exponent!! - strSignificant.length + 1
            refactoring()
            out.postValue(toString())
        } else {
            clear()
            out.postValue(value.toString())
        }
    }
    fun negative(){
        minus = !minus
        out.postValue(toString())
    }
    fun backspace(){
        exponent?.let {
            if (it > 0) {
                exponent = it - 1
                refactoring()
            }
            if (it < 0) exponent = it + 1
            if (it == 0) exponent = null
            if (it + significant.length >= maxLength) {
                out.postValue(toString())
                return
            }
        }
        if (significant.isNotEmpty()) significant.deleteAt(significant.lastIndex)
        out.postValue(toString())
    }
    fun symbol(symbol: Char){
        if (significant.length >= maxLength) return
        when(symbol){
            '.' -> addDot()
            'π' -> setPi()
            else -> addNumber(symbol)
        }
        out.postValue(toString())
    }

    override fun toString(): String {
        exponent?.let {
            if (it == 0){
                return sign() + significant.toDisplayString() + "."
            } else if (it + significant.length > maxLength || it < -maxLength){
                return sign() + scfString()
            } else if (significant.isEmpty() && it < 0){
                val stb = StringBuilder("0.")
                val end = -it
                for (i in 1..end){
                    stb.append('0')
                }
                return sign() + stb.toString()
            } else {
                val stb = StringBuilder(significant)
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
                        stb.append(integer.toLong().toDisplayString())
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
        return sign() + significant.toDisplayString()
    }
    private fun scfString(): String{
        val s = when(significant.length){
            0->{"0.0"}
            1->{significant.toString() + ".0"}
            else->{StringBuilder(significant).insert(1, '.').toString()}
        }
        val eAdd = significant.length - 1
        val e = if (exponent != null) exponent!! + eAdd
        else eAdd
        val ins2 = if (e > 0) "E+${e}"
        else "E${e}"
        return  s + ins2
    }
    private fun addDot(){
        if (exponent == null) exponent = 0
    }
    private fun addNumber(num: Char){
        if (num == '0'){
            if (exponent == null && significant.isEmpty()) return
            else if (significant.isEmpty()) exponent = exponent!! - 1
            else {
                significant.append(num)
                if (exponent != null) exponent = exponent!! - 1
            }
        }else{
            significant.append(num)
            if (exponent != null) exponent = exponent!! - 1
        }

    }
    private fun setPi(){
        setDouble(Math.PI)
    }
    private fun refactoring(){
        if (exponent == 0) exponent = null
        ////TODO FOR MINUS
        exponent?.let {
            if (it > 0 && (significant.length + it) <= maxLength){
                val s = significant.toString().toLong() * base.pow(it).toLong()
                significant.clear()
                significant.append(s.toString())
                exponent = null
            }
        }
    }
    private fun sign(): String {
        return if (minus) "-"
        else ""
    }
}