package com.dmitryluzev.calculator.view.main


import android.app.Application
import androidx.lifecycle.*
import com.dmitryluzev.calculator.core.*
import com.dmitryluzev.calculator.model.Repo
import com.dmitryluzev.calculator.operations.Operation

class MainViewModel(private val repo: Repo, private val calc: Calculator): ViewModel(){

    val bufferDisplay:  LiveData<String> = calc.bufferDisplay
    val memoryDisplay:  LiveData<String> = calc.memoryDisplay
    val operationDisplay: LiveData<List<Operation>> = calc.operationDisplay

    init {
        if (!calc.initialized){
            calc.setState(repo.restoreState())
        }
        calc.setOnResultReadyListener {
            repo.saveToHistory(it)
        }
    }
    fun saveState(){
        repo.saveState(calc.getState())
    }
    //#region calculator forwarding
    fun clearCalc() = calc.clear()
    fun symbol(symbol: Symbols) = calc.symbol(symbol)
    fun negative() = calc.negative()
    fun backspace() = calc.backspace()
    fun result() = calc.result()
    fun operation(operation: Operation)  = calc.operation(operation)
    fun percent() = calc.percent()
    fun memoryClear() = calc.memoryClear()
    fun memoryAdd() = calc.memoryAdd()
    fun memorySubtract() = calc.memorySubtract()
    fun memoryRestore() = calc.memoryRestore()
    fun pasteFromClipboard(value: String): String? = calc.pasteFromClipboard(value)
    fun clearBuffer() = calc.clearBuffer()
    //#endregion
}

class MainViewModelFactory(private val repo: Repo, private val calc: Calculator): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(repo, calc) as T
        }
        throw IllegalArgumentException("ViewModel class isn't MainViewModel")
    }
}