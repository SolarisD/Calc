package com.dmitryluzev.core

import com.dmitryluzev.core.buffer.Converter
import org.junit.Assert
import org.junit.Test

class ConverterUnitTest {
    @Test
    fun doubleToStringTest(){
        var double = 0.0
        Assert.assertEquals("0", Converter.doubleToString(double))

        double = 1234567890.0
        Assert.assertEquals("1 234 567 890", Converter.doubleToString(double))

        double = 1.2
        Assert.assertEquals("1${Converter.ds}2", Converter.doubleToString(double))

        double = 1.2*100000000000.0
        Assert.assertEquals("1${Converter.ds}2E+11", Converter.doubleToString(double))

        double = 1.23456789111*100000000000000.0
        Assert.assertEquals("1${Converter.ds}234567891E+14", Converter.doubleToString(double))

        double = 1.2/100000000000.0
        Assert.assertEquals("1${Converter.ds}2E-11", Converter.doubleToString(double))

        double = 1.23456789111/100000000000000.0
        Assert.assertEquals("1${Converter.ds}234567891E-14", Converter.doubleToString(double))

    }

    @Test
    fun stringToDoubleTest(){
        var double = 0.0
        var string = Converter.doubleToString(double)
        Assert.assertEquals(double, Converter.stringToDouble(string))

        double = 1234567890.0
        string = Converter.doubleToString(double)
        Assert.assertEquals(double, Converter.stringToDouble(string))

        double = 1.2
        string = Converter.doubleToString(double)
        Assert.assertEquals(double, Converter.stringToDouble(string))

        double = 1.2*100000000000.0
        string = Converter.doubleToString(double)
        Assert.assertEquals(double, Converter.stringToDouble(string))

        double = 1.234567891E+14
        string = Converter.doubleToString(double)
        Assert.assertEquals(double, Converter.stringToDouble(string))

        double = 1.2E-11
        string = Converter.doubleToString(double)
        Assert.assertEquals(double, Converter.stringToDouble(string))

        double = 1.234567891E-14
        string = Converter.doubleToString(double)
        Assert.assertEquals(double, Converter.stringToDouble(string))
    }
}