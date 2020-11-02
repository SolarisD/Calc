package com.dmitryluzev.calculator.view.main


import androidx.lifecycle.*
import com.dmitryluzev.calculator.app.Pref
import com.dmitryluzev.calculator.core.*
import com.dmitryluzev.calculator.core.operations.Operation
import com.dmitryluzev.calculator.model.Repo

class MainViewModel(private val calc: Calculator, private val repo: Repo, private val pref: Pref): ViewModel(){
    val bufferDisplay:  LiveData<String> = calc.bufferDisplay
    val memoryDisplay:  LiveData<String> = calc.memoryDisplay
    val operationDisplay: LiveData<List<Operation>> = calc.operationDisplay
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