package com.solarisd.calc.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.solarisd.calc.model.DB
import com.solarisd.calc.model.Dao
import com.solarisd.calc.model.Record

class HistoryViewModel(private val app: Application): AndroidViewModel(app) {
    private val dao: Dao = DB.getInstance(app).dao()
    val historyRecords: LiveData<List<Record>> = dao.getHistoryRecords()
    fun clearHistory() {
        dao.clearHistoryRecords()
    }
}