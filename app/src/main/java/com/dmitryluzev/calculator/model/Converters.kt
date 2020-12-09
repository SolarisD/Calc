package com.dmitryluzev.calculator.model

import androidx.room.TypeConverter
import com.dmitryluzev.core.Operation
import com.dmitryluzev.core.OperationFactory
import java.util.*

class Converters{
    @TypeConverter
    fun operationToString(operation: Operation?): String? = OperationFactory.toStoreString(operation)
    @TypeConverter
    fun stringToOperation(string: String?): Operation? = OperationFactory.fromStoreString(string)
    @TypeConverter
    fun dateToTimestamp(date: Date): Long = date.time
    @TypeConverter
    fun timestampToDate(timestamp: Long): Date = Date(timestamp)
}