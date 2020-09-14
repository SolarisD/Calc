package com.solarisd.calc.core

import androidx.lifecycle.MutableLiveData
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class Buffer() {
    val out: MutableLiveData<String> = MutableLiveData()
    private var integerPart: String? = null
        set(value) {
            field = value
            out.postValue(getString())
        }
    private var delimiter: Char? = null
        set(value) {
            field = value
            out.postValue(getString())
        }
    private var fractionalPart: String? = null
        set(value) {
            field = value
            out.postValue(getString())
        }
    private val s = DecimalFormatSymbols(Locale.US)
    private val f = DecimalFormat()
    init {
        s.groupingSeparator = ' '
        f.decimalFormatSymbols = s
        f.isGroupingUsed = true
    }
    fun clear() {
        integerPart = null
        delimiter = null
        fractionalPart = null
    }
    private fun getString(): String{
        var ret = "0"
        integerPart?.let { ret = f.format(it.toInt()) }
        delimiter?.let { ret += it }
        fractionalPart?.let { ret += it }
        return ret
    }
    fun getDouble(): Double{
        return getString().toDouble()
    }
    fun setDouble(value: Double){
        if (value.isFinite()){
            val str = value.toDisplayString()
            if (str.indexOf('.') == -1){
                integerPart = str
                delimiter = null
                fractionalPart = null
            }else{
                integerPart = str.substringBefore('.')
                delimiter = '.'
                fractionalPart = str.substringAfter('.')
            }
        } else {
            clear()
            out.postValue(value.toString())
        }
    }
    fun symbol(symbol: Char){
        if (getString().length >= 10) return
        when(symbol){
            '0' -> addZero()
            '1' -> addNumber('1')
            '2' -> addNumber('2')
            '3' -> addNumber('3')
            '4' -> addNumber('4')
            '5' -> addNumber('5')
            '6' -> addNumber('6')
            '7' -> addNumber('7')
            '8' -> addNumber('8')
            '9' -> addNumber('9')
            '.' -> addDot()
            'π' -> setPi()
        }
    }
    private fun addDot(){
        if (integerPart == null) integerPart = "0"
        delimiter = '.'
    }
    private fun addZero() {
        if(delimiter == null){//work with integer part
            if (integerPart != null) integerPart += "0"
        } else { //work with fractional part
            if (fractionalPart == null) fractionalPart = "0"
            else fractionalPart += "0"
        }
    }
    private fun addNumber(number: Char){
        if(delimiter == null){//work with integer part
            if (integerPart == null) integerPart = number.toString()
            else integerPart += number
        } else { //work with fractional part
            if (fractionalPart == null) fractionalPart = number.toString()
            else fractionalPart += number
        }
    }
    fun negative(){
        if(integerPart != null){
            if (integerPart!![0] != '-'){
                integerPart = "-${integerPart}"
            } else {
                integerPart = integerPart!!.drop(1)
            }
        }
    }
    fun backspace(){
        if (delimiter != null){//work with fractional part
            if(fractionalPart != null){
                if (fractionalPart!!.length == 1) {fractionalPart = null; delimiter = null}
                else fractionalPart = fractionalPart!!.dropLast(1)
            }
        }else {
            if (integerPart != null){
                if (integerPart!!.length == 1) {integerPart = null}
                else integerPart = integerPart!!.dropLast(1)
            }
        }
    }
    private fun setPi(){
        setDouble(Math.PI)
    }
}
