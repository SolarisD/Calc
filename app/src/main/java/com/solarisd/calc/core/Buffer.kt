package com.solarisd.calc.core

import androidx.lifecycle.MutableLiveData
import com.solarisd.calc.app.AppManager

class Buffer(value: String? = null) {
    val out: MutableLiveData<String> = MutableLiveData()
    var value: Value = Value.getInstance(value)
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
        this.value = Value.getInstance(value)
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
            'π' -> value = Value.getInstance(Math.PI)
            else -> value.addNumber(symbol)
        }
        update()
    }
    private fun update(){
        out.postValue(value.toString())
        AppManager.saveBuffer(value.toString())
    }
}