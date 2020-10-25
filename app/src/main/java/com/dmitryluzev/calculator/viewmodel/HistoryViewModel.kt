package com.dmitryluzev.calculator.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dmitryluzev.calculator.di.scopes.ActivityScope
import com.dmitryluzev.calculator.model.Dao
import com.dmitryluzev.calculator.model.Record
import javax.inject.Inject

@ActivityScope
class HistoryViewModel @Inject constructor(private val dao: Dao): ViewModel() {
    val historyRecords: LiveData<List<Record>> = dao.getHistoryRecords()
    fun clearHistory() {
        dao.clearHistoryRecords()
    }
}