package com.solarisd.calc.core

import androidx.lifecycle.MutableLiveData
import com.solarisd.calc.app.AppManager

class Buffer(value: Value? = null) {
    val out: MutableLiveData<String> = MutableLiveData()
    private val v: Value = value ?: Value()
    init {
        update()
    }
    fun clear() {
        v.clear()
        update()
    }
    fun getValue(): Value {
        return v.copy()
    }
    fun setValue(value: Value){
        v.setValue(value)
        update()
    }
    fun negative(){
        v.negative()
        update()
    }
    fun backspace(){
        v.backspace()
        update()
    }
    fun symbol(symbol: Char){
        when(symbol){
            '.' -> v.addFractional()
            'π' -> v.setDouble(Math.PI)
            else -> v.addNumber(symbol)
        }
        update()
    }
    private fun update(){
        out.postValue(v.toString())
        AppManager.saveBuffer(v.copy())
    }
}