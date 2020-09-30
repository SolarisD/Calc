package com.solarisd.calc.core

import androidx.lifecycle.MutableLiveData
import java.lang.StringBuilder
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import kotlin.math.pow

class Buffer2(value: String? = null) {
    companion object{
        const val length = 10
    }

    val out: MutableLiveData<String> = MutableLiveData()

    private var sign = false
        set(value) {
            field = value
            out.postValue(getDisplay())
        }
    private var significant: Long = 0
        set(value) {
            field = value
            out.postValue(getDisplay())
        }
    private var exponent: Int? = null
        set(value) {
            field = value
            out.postValue(getDisplay())
        }
    private val base = 10.0

    fun clear() {
        sign = false
        significant = 0
        exponent = null
    }
    private fun getDisplay(): String{
        if (exponent == 0) return getDouble().toDisplayString() + "."
        return getDouble().toDisplayString()
    }
    fun getDouble(): Double{
        if (sign) return -significant.toDouble() * base.pow(exponent ?: 0)
        return significant.toDouble() * base.pow(exponent ?: 0)
    }
    fun setDouble(value: Double){
        if (value.isFinite()){
            val fmt = "%.${length - 1}E"
            var scf = String.format(fmt, value)
            sign = scf[0] == '-'
            if (sign) scf = scf.drop(1)
            val strSignificant = scf.substringBefore('E').replace(".", "")
            val stringBuilder = StringBuilder()
            var flag = false
            for (i in (strSignificant.length - 1) downTo 0){
                if (strSignificant[i] != '0' || flag) {
                    stringBuilder.append(strSignificant[i])
                    flag = true
                }
            }
            stringBuilder.reverse()
            if (stringBuilder.length > length) stringBuilder.deleteRange(length, stringBuilder.length)
            significant = stringBuilder.toString().toLong()
            val strExponent= scf.substringAfter('E')
            val expSign = strExponent[0] == '-'
            exponent = if(expSign) -strExponent.drop(1).toInt()
            else strExponent.drop(1).toInt()
            exponent = exponent!! - stringBuilder.length + 1
        } else{
            clear()
            out.postValue(value.toDisplayString())
        }
    }
    private fun getLength(): Int{
        return significant.toString().length
    }
    fun symbol(symbol: Char){
        if (getLength() >= length) return
        when(symbol){
            '0' -> addNumber(0)
            '1' -> addNumber(1)
            '2' -> addNumber(2)
            '3' -> addNumber(3)
            '4' -> addNumber(4)
            '5' -> addNumber(5)
            '6' -> addNumber(6)
            '7' -> addNumber(7)
            '8' -> addNumber(8)
            '9' -> addNumber(9)
            '.' -> addDot()
            'π' -> setPi()
        }
    }
    private fun addDot(){
        if (exponent == null) exponent = 0
    }
    private fun addNumber(num: Int){
        significant = 10 * significant + num
        if (exponent != null) exponent = exponent!! - 1
    }
    fun negative(){
        sign = !sign
    }
    fun backspace(){
        if (exponent == 0) exponent = null
        else{
            significant /= 10
            if (exponent != null && exponent!! > 0) exponent = exponent!! - 1
            if (exponent != null && exponent!! < 0) exponent = exponent!! + 1
        }
    }
    private fun setPi(){
        setDouble(Math.PI)
    }
}