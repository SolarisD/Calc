package com.dmitryluzev.core

import androidx.lifecycle.MutableLiveData
import com.dmitryluzev.core.values.Value

class Buffer constructor() {
    val out: MutableLiveData<Value> = MutableLiveData()
    private val v = Value.getInstance()
    var clearRequest = false
        private set
    init {
        post()
    }
    fun clear() {
        v.clear()
        clearRequest = false
        post()
    }
    fun getValue(): Value {
        clearRequest = true
        return Value.getInstance().apply { from(v) }
    }
    fun setValue(value: Value){
        v.from(value)
        post()
    }
    fun negative(){
        clearRequest = false
        v.negative()
        post()
    }
    fun backspace(){
        clearRequest = false
        v.backspace()
        post()
    }
    fun symbol(symbol: Symbols){
        if (clearRequest) clear()
        when(symbol){
            Symbols.ZERO -> v.addNumber('0')
            Symbols.ONE -> v.addNumber('1')
            Symbols.TWO -> v.addNumber('2')
            Symbols.THREE -> v.addNumber('3')
            Symbols.FOUR -> v.addNumber('4')
            Symbols.FIVE -> v.addNumber('5')
            Symbols.SIX -> v.addNumber('6')
            Symbols.SEVEN -> v.addNumber('7')
            Symbols.EIGHT -> v.addNumber('8')
            Symbols.NINE -> v.addNumber('9')
            Symbols.DOT -> v.addDot()
            Symbols.PI -> v.setPi()
        }
        post()
    }
    private fun post(){
        out.value = v
    }
}