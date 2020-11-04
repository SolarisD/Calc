package com.dmitryluzev.calculator.model

import android.content.Context
import androidx.room.*


@Database(entities = [Record::class], version = 10, exportSchema = false)
@TypeConverters(Converters::class)
abstract class DB: RoomDatabase(){
    abstract val dao: Dao
    companion object {
        @Volatile
        private var INSTANCE: DB? = null

        fun getInstance(context: Context): DB{
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, DB::class.java, "calc_db")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}


