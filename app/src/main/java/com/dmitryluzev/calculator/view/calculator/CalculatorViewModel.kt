package com.dmitryluzev.calculator.view.calculator

import android.view.HapticFeedbackConstants
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dmitryluzev.calculator.app.Pref
import com.dmitryluzev.calculator.app.Sound
import com.dmitryluzev.calculator.model.Repo
import com.dmitryluzev.core.Calculator
import com.dmitryluzev.core.Symbols

class CalculatorViewModel(private val calc: Calculator, private val repo: Repo, private val pref: Pref, private val sound: Sound) : ViewModel(){
    val haptic: Boolean
        get() = true//pref.haptic

    val historyDisplay = repo.history
    val aluDisplay: LiveData<String> = Transformations.map(calc.aluOut){ it?.toString() }
    val bufferDisplay: LiveData<String> = Transformations.map(calc.bufferOut){ it?.toString() ?: "0" }
    val memoryDisplay: LiveData<String> = Transformations.map(calc.memoryDisplay){ if (it == null) "" else "M: $it" }

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

    fun clearCalc(view: View) {
        calc.clear()
        haptics(view)
    }
    fun symbol(view: View, symbol: Symbols) {
        calc.symbol(symbol)
        haptics(view)
    }
    fun negative(view: View){
        calc.negative()
        haptics(view)
    }
    fun backspace(view: View){
        calc.backspace()
        haptics(view)
    }
    fun result(view: View){
        calc.result()
        haptics(view)
    }
    fun operation(view: View, id: String){
        calc.operation(id)
        haptics(view)
    }
    fun percent(view: View){
        calc.percent()
        haptics(view)
    }
    fun memoryClear(view: View){
        calc.memoryClear()
        haptics(view)
    }
    fun memoryAdd(view: View){
        calc.memoryAdd()
        haptics(view)
    }
    fun memorySubtract(view: View){
        calc.memorySubtract()
        haptics(view)
    }
    fun memoryRestore(view: View){
        calc.memoryRestore()
        haptics(view)
    }
    fun clearBuffer(view: View): Boolean{
        calc.clearBuffer()
        haptics(view)
        return true
    }
    private fun haptics(view: View? = null){
        if (pref.haptic) view?.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
        if (pref.sound) sound.button()
    }
}

class CalculatorViewModelFactory(private val calc: Calculator, private val repo: Repo, private val pref: Pref, private val sound: Sound): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalculatorViewModel::class.java)){
            return CalculatorViewModel(calc, repo, pref, sound) as T
        }

        throw IllegalArgumentException("ViewModel class isn't KeyboardViewModel")
    }
}