package com.dmitryluzev.calculator.model

import androidx.room.TypeConverter
import com.dmitryluzev.core.operations.base.Operation
import com.dmitryluzev.core.values.Value
import com.dmitryluzev.core.values.toOperation
import com.dmitryluzev.core.values.toValue

class Converters {
    @TypeConverter
    fun operationToString(operation: Operation?): String? {
        operation?.let { return it.toStoreString() }
        return null
    }
    @TypeConverter
    fun stringToOperation(string: String?): Operation? = string.toOperation()
    @TypeConverter
    fun valueToString(value: Value?): String? = value.toString()
    @TypeConverter
    fun stringToValue(string: String?): Value? = string.toValue()
}