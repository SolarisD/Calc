package com.dmitryluzev.calculator.model

import androidx.room.*

@Database(entities = [Record::class, State::class], version = 8, exportSchema = false)
@TypeConverters(Converters::class)
abstract class DB: RoomDatabase(){
    abstract fun dao(): Dao
}


