package com.solarisd.calc.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface Dao {
    @Query("SELECT * FROM history_records ORDER BY ID DESC")
    fun getLiveRecords(): LiveData<List<Record>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(history: Record)

    @Query("DELETE FROM history_records")
    fun deleteAll()
}