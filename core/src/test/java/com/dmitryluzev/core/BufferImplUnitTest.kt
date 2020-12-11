package com.dmitryluzev.core

import com.dmitryluzev.core.buffer.BufferImpl
import com.dmitryluzev.core.buffer.Converter
import com.dmitryluzev.core.buffer.Symbols
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class BufferImplUnitTest {
    private lateinit var buffer: BufferImpl

    @Before //Doing before any test
    fun setup(){
        buffer = BufferImpl()
    }

    //INIT TEST
    @Test
    fun whenInit() {
        Assert.assertEquals(0.0, buffer.get(), 0.0000000001)
        Assert.assertEquals("0", buffer.toString())
    }

    //INPUT TESTS
    @Test
    fun whenAdd10DigitsBeforeDot() {

        buffer.symbol(Symbols.ZERO)
        buffer.symbol(Symbols.ONE)
        buffer.symbol(Symbols.ZERO)
        buffer.symbol(Symbols.TWO)
        buffer.symbol(Symbols.THREE)
        buffer.symbol(Symbols.FOUR)
        buffer.symbol(Symbols.FIVE)
        buffer.symbol(Symbols.SIX)
        buffer.symbol(Symbols.SEVEN)
        buffer.symbol(Symbols.EIGHT)
        buffer.symbol(Symbols.NINE)
        buffer.symbol(Symbols.NINE)//Check overload

        Assert.assertEquals(1023456789.0, buffer.get(), 1.0)
        Assert.assertEquals("1 023 456 789", buffer.toString())
    }

    @Test
    fun whenAdd10DigitsBeforeDotMinus() {

        buffer.negative()
        buffer.symbol(Symbols.ZERO)
        buffer.symbol(Symbols.ONE)
        buffer.symbol(Symbols.ZERO)
        buffer.symbol(Symbols.TWO)
        buffer.symbol(Symbols.THREE)
        buffer.symbol(Symbols.FOUR)
        buffer.symbol(Symbols.FIVE)
        buffer.symbol(Symbols.SIX)
        buffer.symbol(Symbols.SEVEN)
        buffer.symbol(Symbols.EIGHT)
        buffer.symbol(Symbols.NINE)
        buffer.symbol(Symbols.NINE)
        //Check overload

        Assert.assertEquals(-1023456789.0, buffer.get(), 1.0)
        Assert.assertEquals("-1 023 456 789", buffer.toString())
    }

    @Test
    fun whenAdd10DigitsAfterDot() {

        buffer.symbol(Symbols.ZERO)
        buffer.symbol(Symbols.DOT)
        buffer.symbol(Symbols.ZERO)
        buffer.symbol(Symbols.ONE)
        buffer.symbol(Symbols.TWO)
        buffer.symbol(Symbols.THREE)
        buffer.symbol(Symbols.FOUR)
        buffer.symbol(Symbols.FIVE)
        buffer.symbol(Symbols.SIX)
        buffer.symbol(Symbols.SEVEN)
        buffer.symbol(Symbols.EIGHT)
        buffer.symbol(Symbols.NINE)
        buffer.symbol(Symbols.NINE) //Chech overload

        Assert.assertEquals(0.0123456789, buffer.get(), 0.0000000001)
        Assert.assertEquals("0${Converter.ds}0123456789", buffer.toString())
    }

    @Test
    fun whenAdd10DigitsAfterDotMinus() {

        buffer.negative()
        buffer.symbol(Symbols.ZERO)
        buffer.symbol(Symbols.DOT)
        buffer.symbol(Symbols.ZERO)
        buffer.symbol(Symbols.ONE)
        buffer.symbol(Symbols.TWO)
        buffer.symbol(Symbols.THREE)
        buffer.symbol(Symbols.FOUR)
        buffer.symbol(Symbols.FIVE)
        buffer.symbol(Symbols.SIX)
        buffer.symbol(Symbols.SEVEN)
        buffer.symbol(Symbols.EIGHT)
        buffer.symbol(Symbols.NINE)
        buffer.symbol(Symbols.NINE) //Chech overload

        Assert.assertEquals(-0.0123456789, buffer.get(), 0.0000000001)
        Assert.assertEquals("-0${Converter.ds}0123456789", buffer.toString())
    }

    @Test
    fun whenAdd10DigitsMiddleDot() {

        buffer.symbol(Symbols.ONE)
        buffer.symbol(Symbols.ZERO)
        buffer.symbol(Symbols.TWO)
        buffer.symbol(Symbols.THREE)
        buffer.symbol(Symbols.FOUR)
        buffer.symbol(Symbols.DOT)
        buffer.symbol(Symbols.FIVE)
        buffer.symbol(Symbols.SIX)
        buffer.symbol(Symbols.SEVEN)
        buffer.symbol(Symbols.EIGHT)
        buffer.symbol(Symbols.NINE)
        buffer.symbol(Symbols.NINE)//Chech overload

        Assert.assertEquals(10234.56789, buffer.get(), 0.0000001)
        Assert.assertEquals("10 234${Converter.ds}56789", buffer.toString())
    }

    @Test
    fun whenAdd10DigitsMiddleDotMinus() {

        buffer.symbol(Symbols.ONE)
        buffer.symbol(Symbols.ZERO)
        buffer.symbol(Symbols.TWO)
        buffer.symbol(Symbols.THREE)
        buffer.symbol(Symbols.FOUR)
        buffer.symbol(Symbols.DOT)
        buffer.negative()
        buffer.symbol(Symbols.FIVE)
        buffer.symbol(Symbols.SIX)
        buffer.symbol(Symbols.SEVEN)
        buffer.symbol(Symbols.EIGHT)
        buffer.symbol(Symbols.NINE)
        buffer.symbol(Symbols.NINE)//Chech overload

        Assert.assertEquals(-10234.56789, buffer.get(), 0.0000001)
        Assert.assertEquals("-10 234${Converter.ds}56789", buffer.toString())
    }

    //SET/GET VALUE TESTS
    @Test
    fun whenSetGetValues() {

        buffer.set(123456.7899)
        Assert.assertEquals(123456.7899, buffer.get(), 0.00001)
        Assert.assertEquals("123 456${Converter.ds}7899", buffer.toString())

        buffer.set(-123456.7899)
        Assert.assertEquals(-123456.7899, buffer.get(), 0.00001)
        Assert.assertEquals("-123 456${Converter.ds}7899", buffer.toString())

        buffer.set(12345678999009.0)
        Assert.assertEquals(12345679000000.0, buffer.get(), 10.0)
        Assert.assertEquals("1${Converter.ds}2345679E+13", buffer.toString())

        buffer.set(12345678991009.0)
        Assert.assertEquals(12345678990000.0, buffer.get(), 10.0)
        Assert.assertEquals("1${Converter.ds}234567899E+13", buffer.toString())

        buffer.set(0.00127087549823)
        Assert.assertEquals(0.001270875498, buffer.get(), 0.000000000001)
        Assert.assertEquals("1${Converter.ds}270875498E-3", buffer.toString())

        buffer.set(0.00127087549893)
        Assert.assertEquals(0.001270875499, buffer.get(), 0.000000000001)
        Assert.assertEquals("1${Converter.ds}270875499E-3", buffer.toString())
    }

    @Test
    fun whenBackspace() {

        buffer.set(-123456.7899)

        buffer.backspace()
        Assert.assertEquals(-123456.789, buffer.get(), 0.0001)
        Assert.assertEquals("-123 456${Converter.ds}789", buffer.toString())

        buffer.backspace()
        Assert.assertEquals(-123456.78, buffer.get(), 0.001)
        Assert.assertEquals("-123 456${Converter.ds}78", buffer.toString())

        buffer.backspace()
        Assert.assertEquals(-123456.7, buffer.get(), 0.01)
        Assert.assertEquals("-123 456${Converter.ds}7", buffer.toString())

        buffer.backspace()
        Assert.assertEquals(-123456.0, buffer.get(), 0.1)
        Assert.assertEquals("-123 456${Converter.ds}", buffer.toString())

        buffer.backspace()
        Assert.assertEquals(-123456.0, buffer.get(), 0.1)
        Assert.assertEquals("-123 456", buffer.toString())

        buffer.backspace()
        Assert.assertEquals(-12345.0, buffer.get(), 0.1)
        Assert.assertEquals("-12 345", buffer.toString())

        buffer.backspace()
        Assert.assertEquals(-1234.0, buffer.get(), 0.1)
        Assert.assertEquals("-1 234", buffer.toString())

        buffer.backspace()
        Assert.assertEquals(-123.0, buffer.get(), 0.1)
        Assert.assertEquals("-123", buffer.toString())

        buffer.backspace()
        Assert.assertEquals(-12.0, buffer.get(), 0.1)
        Assert.assertEquals("-12", buffer.toString())

        buffer.backspace()
        Assert.assertEquals(-1.0, buffer.get(), 0.1)
        Assert.assertEquals("-1", buffer.toString())

        buffer.backspace()
        Assert.assertEquals(0.0, buffer.get(), 0.1)
        Assert.assertEquals("-0", buffer.toString())
    }

    @Test
    fun whenSetInfinity(){
        buffer.set(Double.POSITIVE_INFINITY)
        Assert.assertEquals(Double.POSITIVE_INFINITY, buffer.get(), 0.01)
        Assert.assertEquals(Converter.doubleToString(Double.POSITIVE_INFINITY), buffer.toString())

        buffer.set(Double.NEGATIVE_INFINITY)
        Assert.assertEquals(Double.NEGATIVE_INFINITY, buffer.get(), 0.01)
        Assert.assertEquals(Converter.doubleToString(Double.NEGATIVE_INFINITY), buffer.toString())

        buffer.set(Double.NaN)
        Assert.assertEquals(Double.NaN, buffer.get(), 0.01)
        Assert.assertEquals(Converter.doubleToString(Double.NaN), buffer.toString())
    }
}