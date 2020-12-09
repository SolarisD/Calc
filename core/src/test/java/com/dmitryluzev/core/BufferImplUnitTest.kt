package com.dmitryluzev.core

import com.dmitryluzev.core.buffer.BufferImpl
import com.dmitryluzev.core.buffer.Symbols
import org.junit.Assert
import org.junit.Test

class BufferImplUnitTest {

    //INIT TEST
    @Test
    fun whenInit() {
        val buffer = BufferImpl()
        Assert.assertEquals(0.0, buffer.get(), 0.0000000001)
        Assert.assertEquals("0", buffer.toString())
    }

    //INPUT TESTS
    @Test
    fun whenAdd10DigitsBeforeDot() {
        val buffer = BufferImpl()
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
        val buffer = BufferImpl()
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
        val buffer = BufferImpl()
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
        val buffer = BufferImpl()
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
        val buffer = BufferImpl()

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
        val buffer = BufferImpl()

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
    //SET VALUE TESTS

}