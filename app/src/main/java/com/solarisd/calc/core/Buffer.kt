package com.solarisd.calc.core

import androidx.lifecycle.MutableLiveData
import java.lang.Exception
import java.math.BigDecimal
import kotlin.math.PI

private const val ERROR_MSG = "Error"

class Buffer() {
    val out: MutableLiveData<String> = MutableLiveData()
    var value: String? = null
        private set(value){
            field = value
            out.postValue(field)
        }

    fun setDecimal(value: BigDecimal){
        this.value = value.toDisplayString()
    }

    fun setString(value: String){
        try {
            this.value = value.fromDisplayString().toDisplayString()
        }catch (e: Exception){
            this.value = ERROR_MSG
        }
    }
    fun symbol(symbol: Char){
        value?.let { if (it.length >= 10) return }
        when(symbol){
            '0' -> addZero()
            '1' -> addNumber('1')
            '2' -> addNumber('2')
            '3' -> addNumber('3')
            '4' -> addNumber('4')
            '5' -> addNumber('5')
            '6' -> addNumber('6')
            '7' -> addNumber('7')
            '8' -> addNumber('8')
            '9' -> addNumber('9')
            '.' -> addDot()
            'π' -> setPi()
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
    private fun setPi(){
        value = PI.toString()
    }
}
