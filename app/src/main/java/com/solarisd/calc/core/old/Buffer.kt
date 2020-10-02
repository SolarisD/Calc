/*
package com.solarisd.calc.core.old

import androidx.lifecycle.MutableLiveData
import com.solarisd.calc.app.AppManager
import com.solarisd.calc.core.Converter
import com.solarisd.calc.core.toDisplayString
import com.solarisd.calc.core.toDoubleFromDisplay

class Buffer(value: String? = null) {
    val out: MutableLiveData<String> = MutableLiveData()
    private var sign: Char? = null
        set(value){
            field = value
            out.postValue(getString())
        }
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
    init {
        value?.let {
            setDouble(it.toDoubleFromDisplay())
        }
    }
    fun clear() {
        sign = null
        integerPart = null
        delimiter = null
        fractionalPart = null
        AppManager.saveBuffer(null)
    }
    private fun getString(): String{
        var ret = "0"
        sign?.let {ret = "-"}
        integerPart?.let {
            if (ret == "-") ret += Converter.f.format(it.toLong())
            else ret = Converter.f.format(it.toLong())
        }
        delimiter?.let { ret += it }
        fractionalPart?.let { ret += it }
        return ret
    }
    fun getDouble(): Double{
        return getString().toDoubleFromDisplay()
    }
    fun setDouble(value: Double){
        if (value.isFinite()){
            val str = value.toDisplayString().replace(" ", "")
            if (str.indexOf('-') == 0) sign = '-'
            else sign = null
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
            out.postValue(value.toDisplayString())
        }
        AppManager.saveBuffer(getDouble())
    }
    private fun getLength(): Int{
        var ret = 0
        integerPart?.let {ret += it.length}
        */
/*delimiter?.let { ret += 1 }*//*

        fractionalPart?.let {ret += it.length}
        return ret
    }
    fun symbol(symbol: Char){
        if (getLength() >= 10) return
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
        AppManager.saveBuffer(getDouble())
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
            if (sign == null){
                sign = '-'
            } else {
                sign = null
            }
            AppManager.saveBuffer(getDouble())
        }
    }
    fun backspace(){
        if (delimiter != null){//work with fractional part
            if(fractionalPart != null){
                if (fractionalPart!!.length == 1) {fractionalPart = null; delimiter = null}
                else fractionalPart = fractionalPart!!.dropLast(1)
                AppManager.saveBuffer(getDouble())
            }
        }else {
            if (integerPart != null){
                if (integerPart!!.length == 1) {integerPart = null; sign = null}
                else integerPart = integerPart!!.dropLast(1)
                AppManager.saveBuffer(getDouble())
            }
        }
    }
    private fun setPi(){
        setDouble(Math.PI)
    }
}
*/
