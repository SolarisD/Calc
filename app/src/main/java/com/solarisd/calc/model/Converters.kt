package com.solarisd.calc.model

import androidx.room.TypeConverter
import com.solarisd.calc.core.enums.Operators
import java.math.BigDecimal

class Converters {
    //BIG DECIMAL
    @TypeConverter
    fun bigDecimalToString(value: BigDecimal?): String? = value?.toPlainString()
    @TypeConverter
    fun stringToBigDecimal(value: String?): BigDecimal? = value?.toBigDecimal()

    //OPERATORS
    @TypeConverter
    fun operatorsToString(value: Operators): String = value.toString()
    @TypeConverter
    fun stringToOperators(value: String): Operators =
        when(value){
            Operators.PLUS.print-> Operators.PLUS
            Operators.MINUS.print-> Operators.MINUS
            Operators.MULTIPLY.print-> Operators.MULTIPLY
            Operators.DIVIDE.print-> Operators.DIVIDE
            Operators.SQR.print-> Operators.SQR
            Operators.SQRT.print-> Operators.SQRT
            Operators.SIN.print-> Operators.SIN
            Operators.COS.print-> Operators.COS
            Operators.TAN.print-> Operators.TAN
            else -> Operators.PLUS
        }
}