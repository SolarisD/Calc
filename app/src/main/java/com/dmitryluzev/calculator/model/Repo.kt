package com.dmitryluzev.calculator.model

import androidx.lifecycle.LiveData
import com.dmitryluzev.core.operations.Operation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class Repo constructor(private val dao: Dao) {
    private val repoScope = CoroutineScope(Dispatchers.IO)
    val history: LiveData<List<Record>> = dao.getAllRecords()
    fun getHistoryFromDate(date: Date?): LiveData<List<Record>> = dao.getRecordsFromDate(date)
    fun saveToHistory(operation: Operation){
        repoScope.launch {
            val date = Date(System.currentTimeMillis())
            dao.insertHistoryRecord(Record(date = date, op = operation))
        }
    }
    fun clearHistory(){
        repoScope.launch {
            dao.clearHistoryRecords()
        }
    }
}
