package com.dmitryluzev.calculator.view.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dmitryluzev.calculator.app.Pref
import com.dmitryluzev.calculator.model.DB
import com.dmitryluzev.calculator.model.Repo
import java.lang.IllegalArgumentException

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

