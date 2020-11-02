package com.dmitryluzev.calculator.core

import com.dmitryluzev.calculator.core.operations.*

fun Double.toValue(): Value{
    return Value(this)
}
fun Double?.toValue(): Value?{
    this?.let {
        return Value(it)
    }
    return null
}
fun String?.toValue(): Value?{
    this?.let {
        try {
            val dbl = Value.df.parse(it.replace(" ", ""))?.toDouble() ?: 0.0
            return Value(dbl)
        }catch(e: Exception){
            return null
        }
    }
    return null
}

fun String?.toOperation(): Operation?{
    this?.let {
        val list = it.split(';')
        var a: Value? = null
        if (list.size > 1 && list[1] != "null") a = list[1].toValue()
        var b: Value? = null
        if (list.size > 2 && list[2] != "null") b = list[2].toValue()
        return when(list[0]){
            ID.ADD.tag -> {
                Add().apply {
                    this.a = a
                    this.b = b
                }
            }
            ID.SUBTRACT.tag -> {
                Subtract().apply {
                    this.a = a
                    this.b = b
                }
            }
            ID.MULTIPLY.tag -> {
                Multiply().apply {
                    this.a = a
                    this.b = b
                }
            }
            ID.DIVIDE.tag ->{
                Divide().apply {
                    this.a = a
                    this.b = b
                }
            }
            else-> null
        }
    }
    return null
}