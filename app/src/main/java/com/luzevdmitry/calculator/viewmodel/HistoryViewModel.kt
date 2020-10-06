package com.luzevdmitry.calculator.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.luzevdmitry.calculator.model.DB
import com.luzevdmitry.calculator.model.Dao
import com.luzevdmitry.calculator.model.Record

class HistoryViewModel(private val app: Application): AndroidViewModel(app) {
    private val dao: Dao = DB.getInstance(app).dao()
    val historyRecords: LiveData<List<Record>> = dao.getHistoryRecords()
    fun clearHistory() {
        dao.clearHistoryRecords()
    }
}