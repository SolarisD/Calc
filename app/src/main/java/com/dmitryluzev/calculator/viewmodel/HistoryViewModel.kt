package com.dmitryluzev.calculator.viewmodel

import androidx.lifecycle.ViewModel
import com.dmitryluzev.calculator.di.scopes.ActivityScope
import com.dmitryluzev.calculator.model.Repo
import javax.inject.Inject

@ActivityScope
class HistoryViewModel @Inject constructor(private val repo: Repo): ViewModel() {
    val historyRecords = repo.history

    fun clearHistory() {
        repo.clearHistory()
    }
}