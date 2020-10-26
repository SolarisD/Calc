package com.dmitryluzev.calculator.core

import android.media.VolumeShaper
import com.dmitryluzev.calculator.operations.Operation
import com.dmitryluzev.calculator.operations.Operations

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
            Operations.ADD_ID -> {
                Operations.Add().apply {
                    this.a = a
                    this.b = b
                }
            }
            Operations.SUBTRACT_ID -> {
                Operations.Subtract().apply {
                    this.a = a
                    this.b = b
                }
            }
            Operations.MULTIPLY_ID -> {
                Operations.Multiply().apply {
                    this.a = a
                    this.b = b
                }
            }
            Operations.DIVIDE_ID ->{
                Operations.Divide().apply {
                    this.a = a
                    this.b = b
                }
            }
            /*Operations.SQR_ID ->{
                Operations.Sqr().apply {
                    this.a = a
                }
            }
            Operations.SQRT_ID ->{
                Operations.Sqrt().apply {
                    this.a = a
                }
            }
            Operations.SIN_ID ->{
                Operations.Sin().apply {
                    this.a = a
                }
            }
            Operations.COS_ID ->{
                Operations.Cos().apply {
                    this.a = a
                }
            }
            Operations.TAN_ID ->{
                Operations.Tan().apply {
                    this.a = a
                }
            }*/
            else-> null
        }
    }
    return null
}