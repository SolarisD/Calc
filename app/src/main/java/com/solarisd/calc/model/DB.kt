package com.solarisd.calc.model

import android.content.Context
import android.media.VolumeShaper
import androidx.room.*
import com.solarisd.calc.app.AppManager.operationToStoreString
import com.solarisd.calc.app.AppManager.storeStringToOperation
import com.solarisd.calc.core.*

@Database(entities = [Record::class, State::class], version = 7, exportSchema = false)
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
        fun operationToString(operation: Operation?): String? = operationToStoreString(operation)
        @TypeConverter
        fun stringToOperation(string: String?): Operation? = storeStringToOperation(string)
        @TypeConverter
        fun valueToString(value: Value?): String? = value.toString()
        @TypeConverter
        fun stringToValue(string: String?): Value? = string.toValue()
    }
}


