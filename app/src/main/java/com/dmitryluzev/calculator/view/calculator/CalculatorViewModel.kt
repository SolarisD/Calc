package com.dmitryluzev.calculator.view.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dmitryluzev.calculator.app.Pref
import com.dmitryluzev.calculator.app.Sound
import com.dmitryluzev.calculator.core.Calculator
import com.dmitryluzev.calculator.core.Symbols
import com.dmitryluzev.calculator.model.Repo

class CalculatorViewModel(private val calc: Calculator, private val repo: Repo, private val pref: Pref, private val sound: Sound) : ViewModel(){
    val haptic: Boolean
        get() = pref.haptic

    val bufferDisplay: LiveData<String> = Transformations.map(calc.bufferOut){ it?.toString() ?: "0" }
    val memoryDisplay: LiveData<String> = Transformations.map(calc.memoryDisplay){ if (it == null) "" else "M: $it" }
    val aluCurrent: LiveData<String> = Transformations.map(calc.aluCurrent){ it?.toString() }
    val aluComplete: LiveData<String> = Transformations.map(calc.aluComplete){ it?.toString()}
    init {
        if (!calc.initialized){
            calc.setState(pref.restoreState())
        }
        calc.setOnResultReadyListener {
            repo.saveToHistory(it)
        }
    }
    fun saveState(){
        pref.saveState(calc.getState())
    }
    fun pasteFromClipboard(value: String): String? = calc.pasteFromClipboard(value)

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

class KeyboardViewModelFactory(private val calc: Calculator, private val repo: Repo, private val pref: Pref, private val sound: Sound): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalculatorViewModel::class.java)){
            return CalculatorViewModel(calc, repo, pref, sound) as T
        }

        throw IllegalArgumentException("ViewModel class isn't KeyboardViewModel")
    }
}