package com.solarisd.calc.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface Dao {
    @Query("SELECT * FROM operation_history")
    fun getAll(): List<History>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(history: History)

    @Query("DELETE FROM operation_history")
    fun deleteAll()
}