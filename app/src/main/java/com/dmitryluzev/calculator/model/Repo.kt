package com.dmitryluzev.calculator.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.dmitryluzev.calculator.app.Pref
import com.dmitryluzev.calculator.core.Calculator
import com.dmitryluzev.calculator.operations.Operation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repo @Inject constructor(private val dao: Dao, private val pref: Pref) {
    val history: LiveData<List<Operation>> = Transformations.map(dao.getHistoryRecords()){ it.map { it.op }}
    fun saveToHistory(operation: Operation){
        GlobalScope.launch(Dispatchers.IO) {
            dao.insertHistoryRecord(Record(op = operation))
        }
    }
    fun clearHistory(){
        GlobalScope.launch(Dispatchers.IO) {
            dao.clearHistoryRecords()
        }
    }
    fun saveState(state: Calculator.State) = pref.saveState(state)
    fun restoreState(): Calculator.State = pref.restoreState()
}
