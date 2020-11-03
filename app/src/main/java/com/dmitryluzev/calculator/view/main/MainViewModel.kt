package com.dmitryluzev.calculator.view.main


import androidx.lifecycle.*
import com.dmitryluzev.calculator.app.Pref
import com.dmitryluzev.calculator.core.*
import com.dmitryluzev.calculator.model.Repo

class MainViewModel(private val calc: Calculator, private val repo: Repo, private val pref: Pref): ViewModel(){
    val bufferDisplay = calc.bufferDisplay
    val memoryDisplay = calc.memoryDisplay
    val aluCurrent = calc.aluCurrent
    val aluComplete = calc.aluComplete
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
}

class MainViewModelFactory(private val calc: Calculator, private val repo: Repo, private val pref: Pref): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(calc, repo, pref) as T
        }
        throw IllegalArgumentException("ViewModel class isn't MainViewModel")
    }
}