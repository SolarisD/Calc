package com.solarisd.calc.core

import androidx.lifecycle.MutableLiveData
import com.solarisd.calc.app.AppManager

class Buffer(value: Value? = null) {
    val out: MutableLiveData<String> = MutableLiveData()
    private val v: Value = value ?: Value()
    init {
        post()
    }
    fun clear() {
        v.clear()
        post()
    }
    fun getValue(): Value {
        return v.copy()
    }
    fun setValue(value: Value){
        v.setValue(value)
        post()
    }
    fun negative(){
        v.negative()
        post()
    }
    fun backspace(){
        v.backspace()
        post()
    }
    fun symbol(symbol: Char){
        when(symbol){
            '.' -> v.addFractional()
            'π' -> v.setDouble(Math.PI)
            else -> v.addNumber(symbol)
        }
        post()
    }
    private fun post(){
        out.postValue(v.toString())
        AppManager.saveBuffer(v.copy())
    }
}