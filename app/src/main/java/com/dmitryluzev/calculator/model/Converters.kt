package com.dmitryluzev.calculator.model

import androidx.room.TypeConverter
import com.dmitryluzev.calculator.core.Value
import com.dmitryluzev.calculator.core.operations.Operation
import com.dmitryluzev.calculator.core.toOperation
import com.dmitryluzev.calculator.core.toValue

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