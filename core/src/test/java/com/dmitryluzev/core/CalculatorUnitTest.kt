package com.dmitryluzev.core

import com.dmitryluzev.core.buffer.Symbols
import com.dmitryluzev.core.calculator.Calculator
import com.dmitryluzev.core.calculator.State
import com.dmitryluzev.core.operations.OperationFactory
import com.dmitryluzev.core.operations.Subtract
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CalculatorUnitTest {
    private lateinit var calculator: Calculator
    private lateinit var state: State

    @Before
    fun setup(){
        calculator = Calculator()
    }

    @Test
    fun initCalc(){
        state = calculator.get()
        Assert.assertNotNull(state.buffer)
        Assert.assertNull(state.memory)
        Assert.assertNull(state.operation)

        calculator = Calculator(State())
        state = calculator.get()
        Assert.assertNotNull(state.buffer)
        Assert.assertNull(state.memory)
        Assert.assertNull(state.operation)

        calculator = Calculator(State(3.0, null, 9.5, OperationFactory.create(OperationFactory.DIVIDE_ID, 45.0, 9.0)))
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
        calculator.bSet(69.0)
        Assert.assertEquals(69.0, calculator.get().buffer)
        calculator.mAdd()
        Assert.assertEquals(69.0, calculator.get().memory)
        calculator.mSubtract()
        Assert.assertEquals(0.0, calculator.get().memory)
        calculator.mSubtract()
        Assert.assertEquals(-69.0, calculator.get().memory)
        calculator.bClear()
        Assert.assertEquals(0.0, calculator.get().buffer)
        calculator.mRestore()
        Assert.assertEquals(-69.0, calculator.get().buffer)
        calculator.mClear()
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
        calculator.bSet(14.5)
        calculator.operation(OperationFactory.ADD_ID)
        Assert.assertNotNull(calculator.get().operation)
        calculator.bSet(7.13)
        calculator.result()
        Assert.assertNotNull(calculator.get().operation)
        Assert.assertNotNull(calculator.get().operation!!.result())
        Assert.assertEquals(14.5 + 7.13, calculator.get().operation!!.result()!!, 0.0001)
        Assert.assertEquals(14.5 + 7.13, calculator.get().buffer!!, 0.0001)
    }

    @Test
    fun whenChangeBinaryAndEqual(){
        calculator.bSet(14.5)
        calculator.operation(OperationFactory.ADD_ID)
        calculator.operation(OperationFactory.SUBTRACT_ID)
        Assert.assertNotNull(calculator.get().operation)
        calculator.bSet(7.13)
        calculator.result()
        Assert.assertNotNull(calculator.get().operation)
        Assert.assertNotNull(calculator.get().operation!!.result())
        Assert.assertEquals(14.5 - 7.13, calculator.get().operation!!.result()!!, 0.0001)
        Assert.assertEquals(14.5 - 7.13, calculator.get().buffer!!, 0.0001)
    }

    @Test
    fun whenEqualBinaryRepeat(){
        calculator.bSet(14.5)
        calculator.operation(OperationFactory.ADD_ID)
        calculator.bSet(7.13)
        calculator.result()
        calculator.result()
        Assert.assertNotNull(calculator.get().operation)
        Assert.assertNotNull(calculator.get().operation!!.result())
        Assert.assertEquals(14.5 + 7.13 + 7.13, calculator.get().operation!!.result()!!, 0.0001)
        Assert.assertEquals(14.5 + 7.13 + 7.13, calculator.get().buffer!!, 0.0001)
    }

    @Test
    fun whenEqualBinaryWithoutB(){
        calculator.bSet(14.5)
        calculator.operation(OperationFactory.ADD_ID)
        calculator.result()
        Assert.assertNotNull(calculator.get().operation)
        Assert.assertNotNull(calculator.get().operation!!.result())
        Assert.assertEquals(14.5 + 14.5, calculator.get().operation!!.result()!!, 0.0001)
        Assert.assertEquals(14.5 + 14.5, calculator.get().buffer!!, 0.0001)
    }

    @Test
    fun whenOperationAfterOperation(){
        calculator.bSet(14.5)
        calculator.operation(OperationFactory.ADD_ID)
        calculator.bSet(7.13)
        calculator.operation(OperationFactory.SUBTRACT_ID)
        Assert.assertEquals(14.5 + 7.13, calculator.get().buffer!!, 0.0001)
        Assert.assertTrue(calculator.get().operation is Subtract)
        Assert.assertNull(calculator.get().operation!!.result())
        calculator.bSet(2.2)
        calculator.result()
        Assert.assertNotNull(calculator.get().operation)
        Assert.assertNotNull(calculator.get().operation!!.result())
        Assert.assertEquals(14.5 + 7.13 - 2.2, calculator.get().operation!!.result()!!, 0.0001)
        Assert.assertEquals(14.5 + 7.13 - 2.2, calculator.get().buffer!!, 0.0001)
    }

    @Test
    fun whenPercent(){
        calculator.bSet(50.0)
        calculator.operation(OperationFactory.SUBTRACT_ID)
        calculator.bSet(10.0)
        calculator.percent()
        Assert.assertEquals(50.0 - 5.0, calculator.get().buffer!!, 0.0001)

        calculator.clear()
        calculator.bSet(3.0)
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