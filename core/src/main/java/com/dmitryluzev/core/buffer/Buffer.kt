package com.dmitryluzev.core.buffer

interface Buffer {
    fun get(): Double
    fun set(double: Double)
    fun clear()
    fun negative()
    fun backspace()
    fun symbol(symbol: Symbols)
}