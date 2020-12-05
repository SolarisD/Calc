package com.dmitryluzev.core.values

interface IValue<T> {
    fun set(value: IValue<T>)
    fun clear()
    fun negative()
    fun backspace()
    fun addFractional()
    fun addNumber(num: Char)
    operator fun unaryMinus(): IValue<T>
    operator fun plus(b: IValue<T>): IValue<T>
    operator fun minus(b: IValue<T>): IValue<T>
    operator fun times(b: IValue<T>): IValue<T>
    operator fun div(b: IValue<T>): IValue<T>
}

/*
fun <T>iValueOf(): IValue<T>?{
    when(T){
        is Double -> {return null}
    }
    return null
}*/
