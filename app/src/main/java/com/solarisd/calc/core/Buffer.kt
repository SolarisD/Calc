package com.solarisd.calc.core

import androidx.lifecycle.MutableLiveData
import com.solarisd.calc.app.AppManager
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import kotlin.math.pow
import kotlin.text.StringBuilder

class Buffer(value: String? = null) {
    val out: MutableLiveData<String> = MutableLiveData()
    var value: Value = Value.fromString(value)
    init {
        out.postValue(this.value.toString())
    }

    fun clear() {
        value.clear()
        update()
    }
    fun getDouble(): Double{
        return value.toDouble()
    }
    fun setDouble(value: Double){
        this.value = Value.fromDouble(value)
        update()
    }
    fun negative(){
        value.negative()
        update()
    }
    fun backspace(){
        value.backspace()
        update()
    }
    fun symbol(symbol: Char){
        when(symbol){
            '.' -> value.addFractional()
            'π' -> value = Value.fromDouble(Math.PI)
            else -> value.addNumber(symbol)
        }
        update()
    }
    private fun update(){
        out.postValue(value.toString())
        AppManager.saveBuffer(value.toString())
    }
}