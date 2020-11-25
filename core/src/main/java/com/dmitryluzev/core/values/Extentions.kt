package com.dmitryluzev.core.values

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