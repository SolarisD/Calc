package com.dmitryluzev.basic

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SymbolBufferUnitTest {
    private lateinit var buffer: SymbolBuffer

    @Before
    fun setup(){
        buffer = SymbolBuffer()
    }

    @Test
    fun whenSymbolBufferInitOk(){
        buffer = SymbolBuffer("2.35", 12)
        Assert.assertEquals("2.35", buffer.get())
    }

    @Test(expected = IllegalArgumentException::class)
    fun whenInitWithZeroSize(){
        buffer = SymbolBuffer("2.35", 0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun whenInitWithDigitsCountOverDisplaySize(){
        buffer = SymbolBuffer("-222.3519", 6)
    }

    @Test
    fun whenClear(){
        buffer = SymbolBuffer("112", 6)
        buffer.clear()
        Assert.assertEquals("", buffer.get())
    }

    @Test
    fun whenBackspace(){
        buffer = SymbolBuffer("-112.3", 6)
        buffer.backspace()
        Assert.assertEquals("-112.", buffer.get())
        buffer.backspace()
        Assert.assertEquals("-112", buffer.get())
        buffer.backspace()
        Assert.assertEquals("-11", buffer.get())
        buffer.backspace()
        Assert.assertEquals("-1", buffer.get())
        buffer.backspace()
        Assert.assertEquals("", buffer.get())
    }

    @Test
    fun whenSign(){
        //With EMPTY
        buffer.symbol(SymbolBuffer.NEGATIVE)
        Assert.assertEquals("-0", buffer.get())
        buffer.symbol(SymbolBuffer.NEGATIVE)
        Assert.assertEquals("", buffer.get())
        //WITH NUMBER
        buffer = SymbolBuffer("-1", 6)
        buffer.symbol(SymbolBuffer.NEGATIVE)
        Assert.assertEquals("1", buffer.get())
        buffer.symbol(SymbolBuffer.NEGATIVE)
        Assert.assertEquals("-1", buffer.get())
    }

    @Test
    fun whenDelimiter(){
        //With empty
        buffer.symbol(SymbolBuffer.DELIMITER)
        Assert.assertEquals("0.", buffer.get())
        buffer.symbol(SymbolBuffer.DELIMITER)
        Assert.assertEquals("0.", buffer.get())
        //With number
        buffer = SymbolBuffer("-1", 6)
        buffer.symbol(SymbolBuffer.DELIMITER)
        Assert.assertEquals("-1.", buffer.get())
        buffer.symbol(SymbolBuffer.DELIMITER)
        Assert.assertEquals("-1.", buffer.get())
    }

    @Test
    fun whenZero(){
        buffer.symbol(SymbolBuffer.ZERO)
        Assert.assertEquals("0", buffer.get())
        buffer.symbol(SymbolBuffer.ZERO)
        Assert.assertEquals("0", buffer.get())
        buffer.symbol(SymbolBuffer.NEGATIVE)
        buffer.symbol(SymbolBuffer.ZERO)
        Assert.assertEquals("-0", buffer.get())
        buffer.symbol(SymbolBuffer.DELIMITER)
        buffer.symbol(SymbolBuffer.ZERO)
        Assert.assertEquals("-0.0", buffer.get())
        buffer.symbol(SymbolBuffer.ZERO)
        Assert.assertEquals("-0.00", buffer.get())

        buffer = SymbolBuffer("1", 5)
        buffer.symbol(SymbolBuffer.ZERO)
        Assert.assertEquals("10", buffer.get())
        buffer.symbol(SymbolBuffer.ZERO)
        buffer.symbol(SymbolBuffer.ZERO)
        buffer.symbol(SymbolBuffer.DELIMITER)
        buffer.symbol(SymbolBuffer.ZERO)
        buffer.symbol(SymbolBuffer.ZERO)
        Assert.assertEquals("1000.0", buffer.get())
    }

    @Test
    fun whenNonZeroDigits(){
        //With empty
        buffer.symbol(SymbolBuffer.ONE)
        Assert.assertEquals("1", buffer.get())
        //With zero
        buffer = SymbolBuffer("0", 10)
        buffer.symbol(SymbolBuffer.ONE)
        Assert.assertEquals("1", buffer.get())
        buffer.symbol(SymbolBuffer.TWO)
        Assert.assertEquals("12", buffer.get())
        buffer.symbol(SymbolBuffer.THREE)
        Assert.assertEquals("123", buffer.get())
        buffer.symbol(SymbolBuffer.FOUR)
        Assert.assertEquals("1234", buffer.get())
        buffer.symbol(SymbolBuffer.FIVE)
        Assert.assertEquals("12345", buffer.get())
        buffer.symbol(SymbolBuffer.SIX)
        Assert.assertEquals("123456", buffer.get())
        buffer.symbol(SymbolBuffer.SEVEN)
        Assert.assertEquals("1234567", buffer.get())
        buffer.symbol(SymbolBuffer.EIGHT)
        Assert.assertEquals("12345678", buffer.get())
        buffer.symbol(SymbolBuffer.NINE)
        Assert.assertEquals("123456789", buffer.get())

    }

    @Test
    fun whenOverrange(){
        //RANGE CONTROL
        buffer = SymbolBuffer("1234567890", 10)
        buffer.symbol(SymbolBuffer.ZERO)
        Assert.assertEquals("1234567890", buffer.get())
        buffer.symbol(SymbolBuffer.ONE)
        Assert.assertEquals("1234567890", buffer.get())
        buffer.symbol(SymbolBuffer.NEGATIVE)
        Assert.assertEquals("-1234567890", buffer.get())
        buffer.symbol(SymbolBuffer.DELIMITER)
        Assert.assertEquals("-1234567890.", buffer.get())
        buffer.symbol(SymbolBuffer.ONE)
        Assert.assertEquals("-1234567890.", buffer.get())
    }

}