package com.solarisd.calc.core

import com.solarisd.calc.core.enums.Symbols
import java.math.BigDecimal

class InputBuffer() {
    var value: String? = null
        private set

    fun setDecimal(value: BigDecimal){
        this.value = value.toString()
    }
    fun symbol(symbol: Symbols){
        value?.let { if (it.length >= 10) return }
        when(symbol){
            Symbols.ZERO -> addZero()
            Symbols.ONE -> addNumber('1')
            Symbols.TWO -> addNumber('2')
            Symbols.THREE -> addNumber('3')
            Symbols.FOUR -> addNumber('4')
            Symbols.FIVE -> addNumber('5')
            Symbols.SIX -> addNumber('6')
            Symbols.SEVEN -> addNumber('7')
            Symbols.EIGHT -> addNumber('8')
            Symbols.NINE -> addNumber('9')
            Symbols.DOT -> addDot()
        }
    }
    fun clear() {
        value = null
    }
    fun negative(){
        if(value != null && value != "0"){
            if (value!![0] != '-'){
                value = "-${value}"
            } else {
                value = value!!.drop(1)
            }
        }
    }
    fun backspace(){
        if(value != null){
            if (value!!.length == 1) value = null
            else value = value!!.dropLast(1)
        }
    }
    private fun addNumber(number: Char){
        if (value == null || value == "0") value = number.toString()
        else value += number.toString()
    }
    private fun addZero() {
        if (value == null) value = "0"
        else if (value != "0") value += "0"
    }
    private fun addDot(){
        if (value == null) value = "0."
        else if (!value!!.contains('.')) value += '.'
    }
}
