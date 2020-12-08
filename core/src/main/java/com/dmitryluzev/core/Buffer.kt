package com.dmitryluzev.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.math.pow

class Buffer {
    enum class Symbols {
        ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, DOT
    }

    private val _out = MutableLiveData<String>()

    val out: LiveData<String>
        get() = _out

    var clearRequest = false
        private set

    private val value = Value()

    init {
        _out.value = value.toString()
    }

    fun get(): Double {
        clearRequest = true
        return value.toDouble()
    }

    fun set(double: Double){
        value.set(double)
        _out.value = value.toString()
    }

    fun clear() {
        value.clear()
        clearRequest = false
        _out.value = value.toString()
    }

    fun negative(){
        value.s = !value.s
        clearRequest = false
        _out.value = value.toString()
    }

    fun backspace(){
        clearRequest = false
        value.e?.let {
            if (it == 0) {
                value.e = null
                return
            }
            if (it > 0) {
                value.e = it - 1
                value.e?.let {
                    if (it > 0 && (value.m.length + it) <= Converter.maxLength){
                        val s = value.m.toLong() * Converter.base.pow(it).toLong()
                        value.m = s.toString()
                        value.e = null
                    }
                }
            }
            if (it < 0) value.e = it + 1
            if (it + value.m.length >= Converter.maxLength) {
                return
            }
        }
        if (value.m.isNotEmpty()) value.m = value.m.dropLast(1)
        _out.value = value.toString()
    }

    fun symbol(symbol: Symbols){
        if (clearRequest) clear()
        when(symbol){
            Symbols.ZERO -> addNumber('0')
            Symbols.ONE -> addNumber('1')
            Symbols.TWO -> addNumber('2')
            Symbols.THREE -> addNumber('3')
            Symbols.FOUR -> addNumber('4')
            Symbols.FIVE -> addNumber('5')
            Symbols.SIX -> addNumber('6')
            Symbols.SEVEN -> addNumber('7')
            Symbols.EIGHT -> addNumber('8')
            Symbols.NINE -> addNumber('9')
            Symbols.DOT -> addDot()
        }
        _out.value = value.toString()
    }

    private fun addDot(){
        if (value.m.length >= Converter.maxLength) return
        if (value.e == null) value.e = 0
    }

    private fun addNumber(num: Char){
        if (value.m.length >= Converter.maxLength) return
        if (num == '0'){
            if (value.e == null && value.m.isEmpty()) return
            else if (value.m.isEmpty()) value.e = value.e!! - 1
            else {
                value.m += num
                if (value.e != null) value.e = value.e!! - 1
            }
        }else{
            value.m += num
            if (value.e != null) value.e = value.e!! - 1
        }
    }
}