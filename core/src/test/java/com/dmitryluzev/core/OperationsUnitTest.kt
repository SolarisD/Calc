package com.dmitryluzev.core.com.dmitryluzev.core

import com.dmitryluzev.core.operations.*
import org.junit.Assert
import org.junit.Test

class OperationsUnitTest {
    private var operation: Operation? = null

    @Test
    fun testFactoryCreate(){
        operation = OperationFactory.create(OperationFactory.ADD_ID)
        Assert.assertTrue(operation is Add)

        operation = OperationFactory.create(OperationFactory.SUBTRACT_ID)
        Assert.assertTrue(operation is Subtract)

        operation = OperationFactory.create(OperationFactory.MULTIPLY_ID)
        Assert.assertTrue(operation is Multiply)

        operation = OperationFactory.create(OperationFactory.DIVIDE_ID)
        Assert.assertTrue(operation is Divide)

        operation = OperationFactory.create(OperationFactory.PERCENT_ID)
        Assert.assertTrue(operation is Percent)
    }

    @Test
    fun testAdd(){
        var bin = OperationFactory.create(OperationFactory.ADD_ID) as BinaryOperation
        Assert.assertNull(bin.a); Assert.assertNull(bin.b); Assert.assertNull(bin.result())

        operation = bin.repeat()
        Assert.assertTrue(operation is Add)

        bin = bin.repeat() as BinaryOperation
        Assert.assertNull(bin.a); Assert.assertNull(bin.b); Assert.assertNull(bin.result())

        bin = OperationFactory.create(OperationFactory.ADD_ID, 1.0, 2.0) as BinaryOperation
        Assert.assertNotNull(bin.a); Assert.assertNotNull(bin.b); Assert.assertNotNull(bin.result())
        Assert.assertEquals(1.0, bin.a!!, 0.01)
        Assert.assertEquals(2.0, bin.b!!, 0.01)
        Assert.assertEquals(3.0, bin.result()!!, 0.01)

        bin = bin.repeat() as BinaryOperation
        Assert.assertNotNull(bin.a); Assert.assertNotNull(bin.b); Assert.assertNotNull(bin.result())
        Assert.assertEquals(3.0, bin.a!!, 0.01)
        Assert.assertEquals(2.0, bin.b!!, 0.01)
        Assert.assertEquals(5.0, bin.result()!!, 0.01)
    }

    @Test
    fun testSub(){
        var bin = OperationFactory.create(OperationFactory.SUBTRACT_ID) as BinaryOperation
        Assert.assertNull(bin.a); Assert.assertNull(bin.b); Assert.assertNull(bin.result())

        operation = bin.repeat()
        Assert.assertTrue(operation is Subtract)

        bin = bin.repeat() as BinaryOperation
        Assert.assertNull(bin.a); Assert.assertNull(bin.b); Assert.assertNull(bin.result())

        bin = OperationFactory.create(OperationFactory.SUBTRACT_ID, 1.0, 2.0) as BinaryOperation
        Assert.assertNotNull(bin.a); Assert.assertNotNull(bin.b); Assert.assertNotNull(bin.result())
        Assert.assertEquals(1.0, bin.a!!, 0.01)
        Assert.assertEquals(2.0, bin.b!!, 0.01)
        Assert.assertEquals(-1.0, bin.result()!!, 0.01)

        bin = bin.repeat() as BinaryOperation
        Assert.assertNotNull(bin.a); Assert.assertNotNull(bin.b); Assert.assertNotNull(bin.result())
        Assert.assertEquals(-1.0, bin.a!!, 0.01)
        Assert.assertEquals(2.0, bin.b!!, 0.01)
        Assert.assertEquals(-3.0, bin.result()!!, 0.01)
    }

    @Test
    fun testMul(){
        var bin = OperationFactory.create(OperationFactory.MULTIPLY_ID) as BinaryOperation
        Assert.assertNull(bin.a); Assert.assertNull(bin.b); Assert.assertNull(bin.result())

        operation = bin.repeat()
        Assert.assertTrue(operation is Multiply)

        bin = bin.repeat() as BinaryOperation
        Assert.assertNull(bin.a); Assert.assertNull(bin.b); Assert.assertNull(bin.result())

        bin = OperationFactory.create(OperationFactory.MULTIPLY_ID, 3.0, 2.0) as BinaryOperation
        Assert.assertNotNull(bin.a); Assert.assertNotNull(bin.b); Assert.assertNotNull(bin.result())
        Assert.assertEquals(3.0, bin.a!!, 0.01)
        Assert.assertEquals(2.0, bin.b!!, 0.01)
        Assert.assertEquals(6.0, bin.result()!!, 0.01)

        bin = bin.repeat() as BinaryOperation
        Assert.assertNotNull(bin.a); Assert.assertNotNull(bin.b); Assert.assertNotNull(bin.result())
        Assert.assertEquals(6.0, bin.a!!, 0.01)
        Assert.assertEquals(2.0, bin.b!!, 0.01)
        Assert.assertEquals(12.0, bin.result()!!, 0.01)
    }

    @Test
    fun testDiv(){
        var bin = OperationFactory.create(OperationFactory.DIVIDE_ID) as BinaryOperation
        Assert.assertNull(bin.a); Assert.assertNull(bin.b); Assert.assertNull(bin.result())

        operation = bin.repeat()
        Assert.assertTrue(operation is Divide)

        bin = bin.repeat() as BinaryOperation
        Assert.assertNull(bin.a); Assert.assertNull(bin.b); Assert.assertNull(bin.result())

        bin = OperationFactory.create(OperationFactory.DIVIDE_ID, 3.0, 2.0) as BinaryOperation
        Assert.assertNotNull(bin.a); Assert.assertNotNull(bin.b); Assert.assertNotNull(bin.result())
        Assert.assertEquals(3.0, bin.a!!, 0.01)
        Assert.assertEquals(2.0, bin.b!!, 0.01)
        Assert.assertEquals(1.5, bin.result()!!, 0.01)

        bin = bin.repeat() as BinaryOperation
        Assert.assertNotNull(bin.a); Assert.assertNotNull(bin.b); Assert.assertNotNull(bin.result())
        Assert.assertEquals(1.5, bin.a!!, 0.01)
        Assert.assertEquals(2.0, bin.b!!, 0.01)
        Assert.assertEquals(0.75, bin.result()!!, 0.01)
    }

    @Test
    fun testPercent(){
        var bin = OperationFactory.create(OperationFactory.PERCENT_ID) as BinaryOperation
        Assert.assertNull(bin.a); Assert.assertNull(bin.b); Assert.assertNull(bin.result())

        operation = bin.repeat()
        Assert.assertTrue(operation is Percent)

        bin = bin.repeat() as BinaryOperation
        Assert.assertNull(bin.a); Assert.assertNull(bin.b); Assert.assertNull(bin.result())

        bin = OperationFactory.create(OperationFactory.PERCENT_ID, 2.0, 50.0) as BinaryOperation
        Assert.assertNotNull(bin.a); Assert.assertNotNull(bin.b); Assert.assertNotNull(bin.result())
        Assert.assertEquals(2.0, bin.a!!, 0.01)
        Assert.assertEquals(50.0, bin.b!!, 0.01)
        Assert.assertEquals(1.0, bin.result()!!, 0.01)

        bin = bin.repeat() as BinaryOperation
        Assert.assertNotNull(bin.a); Assert.assertNotNull(bin.b); Assert.assertNotNull(bin.result())
        Assert.assertEquals(1.0, bin.a!!, 0.01)
        Assert.assertEquals(50.0, bin.b!!, 0.01)
        Assert.assertEquals(0.5, bin.result()!!, 0.01)
    }

    @Test
    fun whenComparesEquals(){
        operation = OperationFactory.create(OperationFactory.ADD_ID, 4.9, 2.0)

        var second = operation
        Assert.assertTrue(operation == second)

        second = OperationFactory.create(OperationFactory.ADD_ID, 4.9, 2.0)
        Assert.assertTrue(operation == second)
    }

    @Test
    fun whenComparesNotEquals(){
        operation = OperationFactory.create(OperationFactory.ADD_ID, 4.9, 2.0)

        var second = OperationFactory.create(OperationFactory.ADD_ID, 4.9, 3.0)
        Assert.assertFalse(operation == second)

        second = OperationFactory.create(OperationFactory.ADD_ID, 5.9, 2.0)
        Assert.assertFalse(operation == second)

        second = OperationFactory.create(OperationFactory.MULTIPLY_ID, 4.9, 2.0)
        Assert.assertFalse(operation == second)
    }
}