package com.dmitryluzev.calculator.model

import androidx.lifecycle.LiveData
import com.dmitryluzev.core.operations.base.Operation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class Repo constructor(private val dao: Dao) {
    val history: LiveData<List<Record>> = dao.getAllRecords()
    fun getHistoryFromDate(date: Date?): LiveData<List<Record>> = dao.getRecordsFromDate(date)
    fun saveToHistory(operation: Operation){
        GlobalScope.launch(Dispatchers.IO) {
            val date = Date(System.currentTimeMillis())
            dao.insertHistoryRecord(Record(date = date, op = operation))
        }
    }
    fun clearHistory(){
        GlobalScope.launch(Dispatchers.IO) {
            dao.clearHistoryRecords()
        }
    }
}
