package com.dmitryluzev.core.memory

interface Memory {
    fun clear()
    fun add(value: Double)
    fun sub(value: Double)
    fun get(): Double?
}