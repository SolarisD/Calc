package com.dmitryluzev.core.basic

import com.dmitryluzev.core.buffer.Symbols

interface Calculator {
    fun clear()
    fun clearBuffer()
    fun setBuffer(double: Double)
    fun symbol(symbol: Char)
    fun backspace()
    /*fun clearMem()
    fun addMem()
    fun subMem()
    fun restoreMem()*/
}