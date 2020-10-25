package com.dmitryluzev.calculator.viewmodel

import android.media.VolumeShaper
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dmitryluzev.calculator.di.scopes.ActivityScope
import com.dmitryluzev.calculator.model.Dao
import com.dmitryluzev.calculator.model.Record
import com.dmitryluzev.calculator.operations.Operation
import javax.inject.Inject

@ActivityScope
class HistoryViewModel @Inject constructor(private val dao: Dao): ViewModel() {
    val historyRecords: LiveData<List<Operation>> =
        Transformations.map(dao.getHistoryRecords()){ it.map { it.op }}

    fun clearHistory() {
        dao.clearHistoryRecords()
    }
}