package com.dmitryluzev.calculator.model

import androidx.room.TypeConverter
import com.dmitryluzev.calculator.core.Value
import com.dmitryluzev.calculator.core.toValue
import com.dmitryluzev.calculator.operations.BinaryOperation
import com.dmitryluzev.calculator.operations.Operation
import com.dmitryluzev.calculator.operations.Operations
import com.dmitryluzev.calculator.operations.UnaryOperation

class Converters {
    @TypeConverter
    fun operationToString(operation: Operation?): String? =
        when(operation){
            is UnaryOperation -> {
                "${operation.id};${operation.a}"
            }
            is BinaryOperation -> {
                "${operation.id};${operation.a};${operation.b}"
            }
            else-> null
        }
    @TypeConverter
    fun stringToOperation(string: String?): Operation?{
        string?.let {
            val list = it.split(';')
            var a: Value? = null
            if (list.size > 1 && list[1] != "null") a = list[1].toValue()
            var b: Value? = null
            if (list.size > 2 && list[2] != "null") b = list[2].toValue()
            return when(list[0]){
                Operations.ADD_ID -> {
                    Operations.Add().apply {
                        this.a = a
                        this.b = b
                    }
                }
                Operations.SUBTRACT_ID -> {
                    Operations.Subtract().apply {
                        this.a = a
                        this.b = b
                    }
                }
                Operations.MULTIPLY_ID -> {
                    Operations.Multiply().apply {
                        this.a = a
                        this.b = b
                    }
                }
                Operations.DIVIDE_ID ->{
                    Operations.Divide().apply {
                        this.a = a
                        this.b = b
                    }
                }
                /*Operations.SQR_ID ->{
                    Operations.Sqr().apply {
                        this.a = a
                    }
                }
                Operations.SQRT_ID ->{
                    Operations.Sqrt().apply {
                        this.a = a
                    }
                }
                Operations.SIN_ID ->{
                    Operations.Sin().apply {
                        this.a = a
                    }
                }
                Operations.COS_ID ->{
                    Operations.Cos().apply {
                        this.a = a
                    }
                }
                Operations.TAN_ID ->{
                    Operations.Tan().apply {
                        this.a = a
                    }
                }*/
                else-> null
            }
        }
        return null
    }
    @TypeConverter
    fun valueToString(value: Value?): String? = value.toString()
    @TypeConverter
    fun stringToValue(string: String?): Value? = string.toValue()
}