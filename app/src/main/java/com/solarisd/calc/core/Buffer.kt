package com.solarisd.calc.core

import androidx.lifecycle.MutableLiveData
import com.solarisd.calc.app.AppManager

class Buffer(value: String? = null) {
    val out: MutableLiveData<String> = MutableLiveData()
    private val value: Value = value.toValue() ?: Value()

    init {
        out.postValue(this.value.toString())
    }

    fun clear() {
        value.clear()
        update()
    }
    fun getDouble(): Double{
        return value.getDouble()
    }
    fun setDouble(value: Double){
        this.value.setDouble(value)
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
            'π' -> value.setDouble(Math.PI)
            else -> value.addNumber(symbol)
        }
        update()
    }
    private fun update(){
        out.postValue(value.toString())
        AppManager.saveBuffer(value.getDouble())
    }
}