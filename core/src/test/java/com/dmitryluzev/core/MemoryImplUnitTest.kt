package com.dmitryluzev.core


import com.dmitryluzev.core.memory.MemoryImpl
import org.junit.Assert.assertEquals
import org.junit.Test

class MemoryImplUnitTest {
    @Test
    fun whenInit() {
        val memory = MemoryImpl()
        assertEquals(null, memory.get())
    }


    @Test
    fun whenAddValues() {
        val memory = MemoryImpl()
        memory.add(1.0)
        assertEquals(1.0, memory.get())
        memory.add(2.8)
        assertEquals(3.8, memory.get())
    }

    @Test
    fun whenSubValues() {
        val memory = MemoryImpl()
        memory.sub(1.0)
        assertEquals(-1.0, memory.get())
        memory.sub(2.8)
        assertEquals(-3.8, memory.get())
    }

    @Test
    fun whenClearValues() {
        val memory = MemoryImpl()
        memory.add(1.0)
        memory.clear()
        assertEquals(null, memory.get())
    }
}