package com.dmitryluzev.calculator.model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.dmitryluzev.calculator.app.Pref
import com.dmitryluzev.calculator.core.Calculator
import com.dmitryluzev.calculator.core.operations.Operation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class Repo private constructor(application: Application) {
    companion object{
        private var instance: Repo? = null
        fun getInstance(application: Application): Repo {
            if (instance == null) {
                instance = Repo(application)
            }
            return instance!!
        }
    }
    private val dao = DB.getInstance(application).dao
    private val pref = Pref.getInstance(application)

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
}
