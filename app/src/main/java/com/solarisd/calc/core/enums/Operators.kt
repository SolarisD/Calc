package com.solarisd.calc.core.enums

enum class Operators(val unary: Boolean, val symbol: String) {
    PLUS(false, "+"), MINUS(false, "-"), MULTIPLY(false, "×"), DIVIDE(false, "÷"),
    SQR(true, "sqr"), SQRT(true, "√"), SIN(true, "sin"), COS(true, "cos"), TAN(true, "tan")
}

fun String.toOperatos(): Operators? = when(this) {
    Operators.PLUS.symbol -> Operators.PLUS
    Operators.MINUS.symbol -> Operators.MINUS
    Operators.MULTIPLY.symbol -> Operators.MULTIPLY
    Operators.DIVIDE.symbol -> Operators.DIVIDE
    Operators.SQR.symbol -> Operators.SQR
    Operators.SQRT.symbol -> Operators.SQRT
    Operators.SIN.symbol -> Operators.SIN
    Operators.COS.symbol -> Operators.COS
    Operators.TAN.symbol -> Operators.TAN
    else -> null
}