package com.solarisd.calc.model

import android.content.Context
import androidx.room.*
import com.solarisd.calc.core.fromDisplayString
import com.solarisd.calc.core.toDisplayString
import java.math.BigDecimal

@Database(entities = [History::class], version = 1)
@TypeConverters(Converters::class)
abstract class DB: RoomDatabase(){
    abstract fun dao(): Dao
    companion object {
        private var instance: DB? = null
        fun getInstance(context: Context): DB{
            instance?.let { return it }
            instance = Room.databaseBuilder(context.applicationContext, DB::class.java, "calc_db").build()
            return instance!!
        }
    }
}

class Converters {
    @TypeConverter
    fun bigDecimalToString(value: BigDecimal?): String? = value?.toDisplayString()
    @TypeConverter
    fun stringToBigDecimal(value: String?): BigDecimal? = value?.fromDisplayString()
}