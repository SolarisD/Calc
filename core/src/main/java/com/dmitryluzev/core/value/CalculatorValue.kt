package com.dmitryluzev.core.value

import kotlin.math.pow

class CalculatorValue: Value(), NumberKeyboard {

    override fun clear() {
        s = false
        m = ""
        e = null
        i = null
    }

    override fun negative(){
        if (i == false) clear()
        s = !s
    }

    override fun backspace(){
        if (i != null) {clear(); return}
        e?.let {
            if (it == 0) {
                e = null
                return
            }
            if (it > 0) {
                e = it - 1
                e?.let {
                    if (it > 0 && (m.length + it) <= maxLength){
                        val s = m.toLong() * base.pow(it).toLong()
                        m = s.toString()
                        e = null
                    }
                }
            }
            if (it < 0) e = it + 1
            if (it + m.length >= maxLength) {
                return
            }
        }
        if (m.isNotEmpty()) m = m.dropLast(1)
    }

    override fun dot(){
        if (m.length >= maxLength) return
        if (e == null) e = 0
    }

    override fun digit(d: Digits){
        if (i != null) clear()
        when(d){
            Digits.ZERO -> addNumber('0')
            Digits.ONE -> addNumber('1')
            Digits.TWO -> addNumber('2')
            Digits.THREE -> addNumber('3')
            Digits.FOUR -> addNumber('4')
            Digits.FIVE -> addNumber('5')
            Digits.SIX -> addNumber('6')
            Digits.SEVEN -> addNumber('7')
            Digits.EIGHT -> addNumber('8')
            Digits.NINE -> addNumber('9')
        }
    }

    private fun addNumber(num: Char){
        val isFull = m.length >= maxLength ||
                (e ?: 0 <= -maxLength)
        if (isFull) return
        if (num == '0'){
            if (e == null && m.isEmpty()) return
            else if (m.isEmpty()) e = e!! - 1
            else {
                m += num
                if (e != null) e = e!! - 1
            }
        }else{
            m += num
            if (e != null) e = e!! - 1
        }
    }
}