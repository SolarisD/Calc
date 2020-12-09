package com.dmitryluzev.core


import com.dmitryluzev.core.memory.MemoryImpl
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MemoryImplUnitTest {

    private lateinit var memory: MemoryImpl

    @Before
    fun setup(){
        memory = MemoryImpl()
    }

    @Test
    fun whenInit() {
        assertEquals(null, memory.get())
    }

    @Test
    fun whenAddValues() {
        memory.add(1.0)
        assertEquals(1.0, memory.get())
        memory.add(2.8)
        assertEquals(3.8, memory.get())
    }

    @Test
    fun whenSubValues() {
        memory.sub(1.0)
        assertEquals(-1.0, memory.get())
        memory.sub(2.8)
        assertEquals(-3.8, memory.get())
    }

    @Test
    fun whenClearValues() {
        memory.add(1.0)
        memory.clear()
        assertEquals(null, memory.get())
    }
}