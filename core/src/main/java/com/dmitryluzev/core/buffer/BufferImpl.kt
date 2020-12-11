package com.dmitryluzev.core.buffer

import kotlin.math.pow

class BufferImpl: Buffer {

    private val value = Value()

    override fun get(): Double = Converter.bufferValueToDouble(value)

    override fun set(double: Double){
        Converter.doubleToBufferValue(double).let {
            value.s = it.s
            value.m = it.m
            value.e = it.e
            value.e = it.e

        }
    }

    override fun clear() {
        value.s = false
        value.m = ""
        value.e = null
        value.u = null
    }

    override fun negative(){
        value.s = !value.s
    }

    override fun backspace(){
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
    }

    override fun symbol(symbol: Symbols){
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
    }

    private fun addDot(){
        if (value.m.length >= Converter.maxLength) return
        if (value.e == null) value.e = 0
    }

    private fun addNumber(num: Char){
        val isFull = value.m.length >= Converter.maxLength ||
                (value.e ?: 0 <= -Converter.maxLength)
        if (isFull) return
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

    override fun toString(): String = Converter.bufferValueToString(value)
}