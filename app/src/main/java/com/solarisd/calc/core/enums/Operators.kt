package com.solarisd.calc.core.enums

val sqr = 178.toChar()

enum class Operators(val unary: Boolean, val symbol: String) {
    PLUS(false, "+"), MINUS(false, "-"), MULTIPLY(false, "×"), DIVIDE(false, "÷"),
    SQR(true, "X${sqr}"), SQRT(true, "√"), SIN(true, "sin"), COS(true, "cos"), TAN(true, "tan"),
    POW_MINUS_ONE(true, "1/X"), POW(false, "X^Y"), LN(true, "ln"), E(true, "e^X")
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