package com.dmitryluzev.calculator.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.*

@Dao
interface Dao {
    @Query("SELECT * FROM history_records ORDER BY ID DESC")
    fun getAllRecords(): LiveData<List<Record>>

    @Query("SELECT * FROM history_records WHERE date > :date ORDER BY ID DESC")
    fun getRecordsFromDate(date: Date?): LiveData<List<Record>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistoryRecord(history: Record)

    @Query("DELETE FROM history_records")
    suspend fun clearHistoryRecords()
}
