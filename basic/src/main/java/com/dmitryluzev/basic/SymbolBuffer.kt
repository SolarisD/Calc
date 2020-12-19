package com.dmitryluzev.basic

class SymbolBuffer(value: String = "", private val displaySize: Int = 12){

    companion object Symbols{
        const val ZERO = '0'
        const val ONE = '1'
        const val TWO = '2'
        const val THREE = '3'
        const val FOUR = '4'
        const val FIVE = '5'
        const val SIX = '6'
        const val SEVEN = '7'
        const val EIGHT = '8'
        const val NINE = '9'
        const val DELIMITER = '.'
        const val NEGATIVE = '±'
    }

    private var sign: Boolean = false

    private var mod: String = ""

    init {
        if (displaySize < 1) throw IllegalArgumentException("Wrong display size")
        if(!set(value)) throw IllegalArgumentException("Wrong init value")
    }

    private fun checkSymbols(value: String): Boolean{
        return digitsCount(value) <= displaySize
        //TODO add more symbol checks
    }

    fun get() = "${if(sign) "-" else ""}$mod"

    fun set(value: String): Boolean{
        if (value.isEmpty()){
            clear()
            return true
        }
        else if (checkSymbols(value)){
            if (value[0] == '-'){
                sign = true
                mod = value.drop(1)
            } else {
                sign = false
                mod = value
            }
            return true
        }
        else return false
    }

    fun clear() {
        sign = false
        mod = ""
    }

    fun backspace() {
        if (mod.isNotEmpty()){
            mod = mod.dropLast(1)
            if (mod.isEmpty()) sign = false
        } else {
            sign = false
        }
    }

    fun symbol(symbol: Char) {
        when(symbol){
            NEGATIVE -> negative()
            DELIMITER -> delimiter()
            ZERO -> zeroDigit()
            ONE -> nonZeroDigit(symbol)
            TWO -> nonZeroDigit(symbol)
            THREE -> nonZeroDigit(symbol)
            FOUR -> nonZeroDigit(symbol)
            FIVE -> nonZeroDigit(symbol)
            SIX -> nonZeroDigit(symbol)
            SEVEN -> nonZeroDigit(symbol)
            EIGHT -> nonZeroDigit(symbol)
            NINE -> nonZeroDigit(symbol)
            else -> throw IllegalArgumentException("Wrong symbol")
        }
    }

    private fun negative(){
        if(mod.isEmpty()){ mod = "0"; sign = true }
        else{
            sign = !sign
            if (!sign && mod == "0") mod = ""
        }
    }

    private fun delimiter(){
        if(mod.isEmpty()) mod = "0$DELIMITER"
        else if(!mod.contains(DELIMITER)) mod = "$mod$DELIMITER"
    }

    private fun zeroDigit(){
        if(mod.isEmpty()) mod = "0"
        else if(mod != "0" && digitsCount(mod) < displaySize) mod = "$mod$ZERO"
    }

    private fun nonZeroDigit(digit: Char){
        if (mod == "0") mod = "$digit"
        else if (digitsCount(mod) < displaySize) mod = "$mod$digit"
    }

    private fun digitsCount(string: String): Int{
        return string.replace(".", "").length
    }
}