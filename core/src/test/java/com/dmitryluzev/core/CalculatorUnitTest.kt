package com.dmitryluzev.core.com.dmitryluzev.core

import com.dmitryluzev.core.CalculatorImpl
import com.dmitryluzev.core.State
import com.dmitryluzev.core.buffer.Symbols
import com.dmitryluzev.core.operations.OperationFactory
import com.dmitryluzev.core.operations.Subtract
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CalculatorUnitTest {
    private lateinit var calculator: CalculatorImpl
    private lateinit var state: State

    @Before
    fun setup(){
        calculator = CalculatorImpl()
    }

    @Test
    fun initCalc(){
        state = calculator.get()
        Assert.assertNotNull(state.buffer)
        Assert.assertNull(state.memory)
        Assert.assertNull(state.operation)

        calculator = CalculatorImpl(State())
        state = calculator.get()
        Assert.assertNotNull(state.buffer)
        Assert.assertNull(state.memory)
        Assert.assertNull(state.operation)

        calculator = CalculatorImpl(State(3.0, null, 9.5, OperationFactory.create(OperationFactory.DIVIDE_ID, 45.0, 9.0)))
        state = calculator.get()
        Assert.assertNotNull(state.buffer)
        Assert.assertNotNull(state.memory)
        Assert.assertNotNull(state.operation)

        Assert.assertEquals(3.0, state.buffer)
        Assert.assertEquals(9.5, state.memory)
        var second = OperationFactory.create(OperationFactory.DIVIDE_ID, 45.0, 9.0)
        Assert.assertTrue(state.operation == second)
        second = OperationFactory.create(OperationFactory.ADD_ID, 45.0, 9.0)
        Assert.assertFalse(state.operation == second)
    }

    @Test
    fun setBufferAndMemory(){
        calculator.setBuffer(69.0)
        Assert.assertEquals(69.0, calculator.get().buffer)
        calculator.addMem()
        Assert.assertEquals(69.0, calculator.get().memory)
        calculator.subMem()
        Assert.assertEquals(0.0, calculator.get().memory)
        calculator.subMem()
        Assert.assertEquals(-69.0, calculator.get().memory)
        calculator.clearBuffer()
        Assert.assertEquals(0.0, calculator.get().buffer)
        calculator.restoreMem()
        Assert.assertEquals(-69.0, calculator.get().buffer)
        calculator.clearMem()
        Assert.assertNull(calculator.get().memory)
    }

    @Test
    fun workWithBufferByButtons(){
        calculator.symbol(Symbols.ZERO)
        Assert.assertEquals(0.0, calculator.get().buffer)
        calculator.symbol(Symbols.ONE)
        Assert.assertEquals(1.0, calculator.get().buffer)
        calculator.symbol(Symbols.DOT)
        Assert.assertEquals(1.0, calculator.get().buffer)
        calculator.symbol(Symbols.TWO)
        Assert.assertEquals(1.2, calculator.get().buffer!!, 0.0001)
        calculator.negative()
        Assert.assertEquals(-1.2, calculator.get().buffer!!, 0.0001)
        calculator.backspace()
        Assert.assertEquals(-1.0, calculator.get().buffer!!, 0.0001)
    }

    @Test
    fun whenEqualBinaryClassic(){
        calculator.setBuffer(14.5)
        calculator.operation(OperationFactory.ADD_ID)
        Assert.assertNotNull(calculator.get().operation)
        calculator.setBuffer(7.13)
        calculator.result()
        Assert.assertNotNull(calculator.get().operation)
        Assert.assertNotNull(calculator.get().operation!!.result())
        Assert.assertEquals(14.5 + 7.13, calculator.get().operation!!.result()!!, 0.0001)
        Assert.assertEquals(14.5 + 7.13, calculator.get().buffer!!, 0.0001)
    }

    @Test
    fun whenChangeBinaryAndEqual(){
        calculator.setBuffer(14.5)
        calculator.operation(OperationFactory.ADD_ID)
        calculator.operation(OperationFactory.SUBTRACT_ID)
        Assert.assertNotNull(calculator.get().operation)
        calculator.setBuffer(7.13)
        calculator.result()
        Assert.assertNotNull(calculator.get().operation)
        Assert.assertNotNull(calculator.get().operation!!.result())
        Assert.assertEquals(14.5 - 7.13, calculator.get().operation!!.result()!!, 0.0001)
        Assert.assertEquals(14.5 - 7.13, calculator.get().buffer!!, 0.0001)
    }

    @Test
    fun whenEqualBinaryRepeat(){
        calculator.setBuffer(14.5)
        calculator.operation(OperationFactory.ADD_ID)
        calculator.setBuffer(7.13)
        calculator.result()
        calculator.result()
        Assert.assertNotNull(calculator.get().operation)
        Assert.assertNotNull(calculator.get().operation!!.result())
        Assert.assertEquals(14.5 + 7.13 + 7.13, calculator.get().operation!!.result()!!, 0.0001)
        Assert.assertEquals(14.5 + 7.13 + 7.13, calculator.get().buffer!!, 0.0001)
    }

    @Test
    fun whenEqualBinaryWithoutB(){
        calculator.setBuffer(14.5)
        calculator.operation(OperationFactory.ADD_ID)
        calculator.result()
        Assert.assertNotNull(calculator.get().operation)
        Assert.assertNotNull(calculator.get().operation!!.result())
        Assert.assertEquals(14.5 + 14.5, calculator.get().operation!!.result()!!, 0.0001)
        Assert.assertEquals(14.5 + 14.5, calculator.get().buffer!!, 0.0001)
    }

    @Test
    fun whenOperationAfterOperation(){
        calculator.setBuffer(14.5)
        calculator.operation(OperationFactory.ADD_ID)
        calculator.setBuffer(7.13)
        calculator.operation(OperationFactory.SUBTRACT_ID)
        Assert.assertEquals(14.5 + 7.13, calculator.get().buffer!!, 0.0001)
        Assert.assertTrue(calculator.get().operation is Subtract)
        Assert.assertNull(calculator.get().operation!!.result())
        calculator.setBuffer(2.2)
        calculator.result()
        Assert.assertNotNull(calculator.get().operation)
        Assert.assertNotNull(calculator.get().operation!!.result())
        Assert.assertEquals(14.5 + 7.13 - 2.2, calculator.get().operation!!.result()!!, 0.0001)
        Assert.assertEquals(14.5 + 7.13 - 2.2, calculator.get().buffer!!, 0.0001)
    }

    @Test
    fun whenPercent(){
        calculator.setBuffer(50.0)
        calculator.operation(OperationFactory.SUBTRACT_ID)
        calculator.setBuffer(10.0)
        calculator.percent()
        Assert.assertEquals(50.0 - 5.0, calculator.get().buffer!!, 0.0001)

        calculator.clear()
        calculator.setBuffer(3.0)
        calculator.percent()
        Assert.assertEquals(3.0/100.0, calculator.get().buffer!!, 0.0001)
    }

    @Test
    fun whenEqualsByButtons(){
        //CLASSIC
        calculator.symbol(Symbols.ONE)
        calculator.symbol(Symbols.TWO)
        calculator.operation(OperationFactory.ADD_ID)
        calculator.symbol(Symbols.THREE)
        calculator.symbol(Symbols.FOUR)
        calculator.result()
        Assert.assertEquals(12.0 + 34.0, calculator.get().buffer!!, 0.0001)
        //AFTER RESULT
        calculator.symbol(Symbols.FIVE)
        calculator.symbol(Symbols.SIX)
        calculator.operation(OperationFactory.SUBTRACT_ID)
        calculator.symbol(Symbols.SEVEN)
        calculator.result()
        Assert.assertEquals(56.0 - 7.0, calculator.get().buffer!!, 0.0001)

    }
}