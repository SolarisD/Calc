package com.solarisd.calc.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [OpHistory::class], version = 1)
@TypeConverters(Converters::class)
abstract class db: RoomDatabase()