package com.solarisd.calc.core

import androidx.lifecycle.MutableLiveData
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import kotlin.math.pow
import kotlin.text.StringBuilder

class Buffer() {
    val out: MutableLiveData<String> = MutableLiveData()
    var value: Value = Value()

    fun clear() {
        value.clear()
        out.postValue(value.toString())
    }
    fun getDouble(): Double{
        return value.toDouble()
    }
    fun setDouble(value: Double){
        this.value = Value.fromDouble(value)
        out.postValue(this.value.toString())
    }
    fun negative(){
        value.negative()
        out.postValue(value.toString())
    }
    fun backspace(){
        value.backspace()
        out.postValue(value.toString())
    }
    fun symbol(symbol: Char){
        when(symbol){
            '.' -> value.addFractional()
            'π' -> value = Value.fromDouble(Math.PI)
            else -> value.addNumber(symbol)
        }
        out.postValue(value.toString())
    }
}