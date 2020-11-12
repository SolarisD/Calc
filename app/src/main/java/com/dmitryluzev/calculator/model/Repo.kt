package com.dmitryluzev.calculator.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.dmitryluzev.core.operations.base.Operation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class Repo constructor(private val dao: Dao) {
    val history: LiveData<List<Record>> = dao.getHistoryRecords()
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
}
