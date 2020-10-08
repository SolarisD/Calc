package com.dmitryluzev.calculator.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dmitryluzev.calculator.model.DB
import com.dmitryluzev.calculator.model.Dao
import com.dmitryluzev.calculator.model.Record

class HistoryViewModel(private val app: Application): AndroidViewModel(app) {
    private val dao: Dao = DB.getInstance(app).dao()
    val historyRecords: LiveData<List<Record>> = dao.getHistoryRecords()
    fun clearHistory() {
        dao.clearHistoryRecords()
    }
}