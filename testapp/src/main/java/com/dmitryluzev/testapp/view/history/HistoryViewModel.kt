package com.dmitryluzev.testapp.view.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dmitryluzev.testapp.model.Repo

class HistoryViewModel(private val repo: Repo): ViewModel() {
    val historyRecords = repo.history
    fun clearHistory() {
        repo.clearHistory()
    }
}

class HistoryViewModelFactory(private val repo: Repo): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HistoryViewModel::class.java)){
            return HistoryViewModel(repo) as T
        }
        throw IllegalArgumentException("ViewModel class isn't HistoryViewModel")
    }
}

