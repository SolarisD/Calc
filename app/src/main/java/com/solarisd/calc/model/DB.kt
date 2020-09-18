package com.solarisd.calc.model

import android.content.Context
import android.media.VolumeShaper
import androidx.room.*
import com.solarisd.calc.core.BinaryOperation
import com.solarisd.calc.core.Operation
import com.solarisd.calc.core.Operations

@Database(entities = [Record::class, State::class], version = 2, exportSchema = false)
@TypeConverters(DB.Converters::class)
abstract class DB: RoomDatabase(){
    abstract fun dao(): Dao
    companion object {
        private var instance: DB? = null
        fun getInstance(context: Context): DB{
            instance?.let { return it }
            instance = Room.databaseBuilder(context.applicationContext, DB::class.java, "calc_db")
                .fallbackToDestructiveMigration()
                .build()
            return instance!!
        }
    }

    class Converters {
        @TypeConverter
        fun operationToString(value: Operation?): String? = ""
        @TypeConverter
        fun stringToOperation(value: String?): Operation? = null
    }
}


