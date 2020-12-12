package com.dmitryluzev.core.value

interface NumberKeyboard {
    fun clear()
    fun negative()
    fun backspace()
    fun dot()
    fun digit(d: Digits)
}