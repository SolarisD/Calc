package com.dmitryluzev.calculator.model

import android.util.Log
import androidx.lifecycle.LiveData
import com.dmitryluzev.core.operations.base.Operation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class Repo constructor(private val dao: Dao) {
    val history: LiveData<List<Record>> = dao.getHistoryRecords()
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
