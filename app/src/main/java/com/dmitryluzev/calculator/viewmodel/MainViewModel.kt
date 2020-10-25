package com.dmitryluzev.calculator.viewmodel


import androidx.lifecycle.*
import com.dmitryluzev.calculator.core.*
import com.dmitryluzev.calculator.di.scopes.ActivityScope
import com.dmitryluzev.calculator.model.Dao
import com.dmitryluzev.calculator.model.Record
import com.dmitryluzev.calculator.operations.Operation
import kotlinx.coroutines.*
import javax.inject.Inject

@ActivityScope
class MainViewModel @Inject constructor(private val dao: Dao, private val calc: Calculator): ViewModel(){

    val bufferDisplay:  LiveData<String> = calc.bufferDisplay
    val memoryDisplay:  LiveData<String> = calc.memoryDisplay
    val aluComplete:  LiveData<String> = calc.aluComplete
    val aluCurrent:  LiveData<String> = calc.aluCurrent

    init {
        if (!calc.initialized){
            viewModelScope.launch(Dispatchers.IO) {
                val state = dao.getState(0)
                withContext(Dispatchers.Main){
                    state?.let {
                        calc.setState(it)
                    }
                }
            }
        }
        calc.setOnResultReadyListener {
            viewModelScope.launch(Dispatchers.IO) {
                dao.insertHistoryRecord(Record(op = it))
            }
        }
    }
    fun saveState(){
        val state = calc.getState()
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertState(state)
        }
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