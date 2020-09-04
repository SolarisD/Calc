package com.solarisd.calc.core.enums

enum class Operators(val unary: Boolean, val print: String) {
    PLUS(false, "+"), MINUS(false, "-"), MULTIPLY(false, "×"), DIVIDE(false, "÷"),
    SQR(true, "sqr"), SQRT(true, "√"), SIN(true, "sin"), COS(true, "cos"), TAN(true, "tan")
}