package com.dmitryluzev.core.values

import com.dmitryluzev.core.operations.OperationFactory
import com.dmitryluzev.core.operations.base.Operation

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
        return  OperationFactory.create(list[0], a, b)
    }
    return null
}