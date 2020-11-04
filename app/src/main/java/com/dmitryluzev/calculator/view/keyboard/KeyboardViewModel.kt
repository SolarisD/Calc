package com.dmitryluzev.calculator.view.keyboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dmitryluzev.calculator.app.Pref
import com.dmitryluzev.calculator.app.Sound
import com.dmitryluzev.calculator.core.Calculator
import com.dmitryluzev.calculator.core.Symbols
import com.dmitryluzev.calculator.core.operations.OperationFactory

class KeyboardViewModel(private val calc: Calculator, private val pref: Pref, private val sound: Sound) : ViewModel(){
    val haptic: Boolean
        get() = pref.haptic

    fun clearCalc() {
        calc.clear()
        playSound()
    }
    fun symbol(symbol: Symbols) {
        calc.symbol(symbol)
        playSound()
    }
    fun negative(){
        calc.negative()
        playSound()
    }
    fun backspace(){
        calc.backspace()
        playSound()
    }
    fun result(){
        calc.result()
        playSound()
    }
    fun operation(id: String){
        calc.operation(id)
        playSound()
    }
    fun percent(){
        calc.percent()
        playSound()
    }
    fun memoryClear(){
        calc.memoryClear()
        playSound()
    }
    fun memoryAdd(){
        calc.memoryAdd()
        playSound()
    }
    fun memorySubtract(){
        calc.memorySubtract()
        playSound()
    }
    fun memoryRestore(){
        calc.memoryRestore()
        playSound()
    }
    fun clearBuffer(): Boolean{
        calc.clearBuffer()
        playSound()
        return true
    }
    private fun playSound(){
        if (pref.sound) sound.button()
    }
}

class KeyboardViewModelFactory(private val calc: Calculator, private val pref: Pref, private val sound: Sound): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(KeyboardViewModel::class.java)){
            return KeyboardViewModel(calc, pref, sound) as T
        }

        throw IllegalArgumentException("ViewModel class isn't KeyboardViewModel")
    }
}