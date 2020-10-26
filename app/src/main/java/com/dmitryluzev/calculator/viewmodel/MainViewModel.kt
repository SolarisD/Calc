package com.dmitryluzev.calculator.viewmodel


import androidx.lifecycle.*
import com.dmitryluzev.calculator.core.*
import com.dmitryluzev.calculator.di.scopes.ActivityScope
import com.dmitryluzev.calculator.model.Repo
import com.dmitryluzev.calculator.operations.Operation
import kotlinx.coroutines.*
import javax.inject.Inject

@ActivityScope
class MainViewModel @Inject constructor(private val calc: Calculator, private val repo: Repo): ViewModel(){

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
    fun clear() = calc.clear()
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