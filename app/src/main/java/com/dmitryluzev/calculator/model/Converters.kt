package com.dmitryluzev.calculator.model

import androidx.room.TypeConverter
import com.dmitryluzev.core.operations.Operation
import com.dmitryluzev.core.operations.OperationFactory
import com.dmitryluzev.core.values.Value
import java.util.*

class Converters{
    @TypeConverter
    fun operationToString(operation: Operation?): String? = OperationFactory.toStoreString(operation)
    @TypeConverter
    fun stringToOperation(string: String?): Operation? = OperationFactory.fromStoreString(string)

    @TypeConverter
    fun valueToString(value: Value?): String? = value.toString()
    @TypeConverter
    fun stringToValue(string: String?): Value? = Value.getInstance(string)
    @TypeConverter
    fun dateToTimestamp(date: Date): Long = date.time
    @TypeConverter
    fun timestampToDate(timestamp: Long): Date = Date(timestamp)
}