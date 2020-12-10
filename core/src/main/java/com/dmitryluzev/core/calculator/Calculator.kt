package com.dmitryluzev.core.Calculator

import com.dmitryluzev.core.buffer.Buffer
import com.dmitryluzev.core.buffer.BufferImpl
import com.dmitryluzev.core.buffer.Symbols
import com.dmitryluzev.core.memory.Memory
import com.dmitryluzev.core.memory.MemoryImpl
import com.dmitryluzev.core.operations.BinaryOperation
import com.dmitryluzev.core.operations.Operation
import com.dmitryluzev.core.operations.OperationFactory
import com.dmitryluzev.core.operations.UnaryOperation

class Calculator(state: State? = null){

    private val buffer: Buffer = BufferImpl()
    private val memory: Memory = MemoryImpl()
    private var operation: Operation? = null

    private var bufferClearRequest = false

    init {
        state?.let {
            it.buffer?.let { buffer.set(it) }
            it.memory?.let { memory.add(it) }
            operation = it.operation
        }
    }
    fun get(): State {
        return State(buffer.get(), memory.get(), operation)
    }
    //BUFFER
    fun bSet(double: Double){
        buffer.set(double)
        bufferClearRequest = false
    }
    fun bClear() {
        buffer.clear()
        bufferClearRequest = false
    }
    fun clear() {
        buffer.clear()
        bufferClearRequest = false
        operation = null
    }
    fun symbol(symbol: Symbols) {
        clearOperationIfComplete()
        if (bufferClearRequest){
            buffer.clear()
            bufferClearRequest = false
        }
        buffer.symbol(symbol)
    }
    fun negative(){
        clearOperationIfComplete()
        bufferClearRequest = false
        buffer.negative()
    }
    fun backspace() {
        clearOperationIfComplete()
        bufferClearRequest = false
        buffer.backspace()
    }
    //MEMORY
    fun mClear() = memory.clear()
    fun mAdd() = memory.add(buffer.get())
    fun mSubtract() = memory.sub(buffer.get())
    fun mRestore(){
        memory.get()?.let {
            buffer.set(it)
        }
    }
    //OPERATIONS
    private fun clearOperationIfComplete(){
        operation?.result()?.let {
            operation = null
        }
    }
    fun result() {
        val op = operation
        when(op){
            is UnaryOperation -> {}
            is BinaryOperation -> {
                if(op.result() == null) {
                    op.b = buffer.get()
                    operation = op
                } else {
                    operation = operation?.repeat()
                }
            }
            else -> {}
        }
    }
    fun operation(id: String) {
        val op = operation
        if (op is BinaryOperation){
            if(op.result() == null){
                if (bufferClearRequest) {
                    //Update current operation
                    val new = OperationFactory.create(id)
                    if (new is BinaryOperation) {
                        new.a = op.a
                        operation = new
                    }
                    return
                }
                else {
                    //Complete current operation
                    op.b = buffer.get()
                    operation = op
                }
            }
        }
        //SET NEW
        operation = OperationFactory.create(id, buffer.get())
    }
    fun percent() {
        if(operation is BinaryOperation){
            val binary = (operation as BinaryOperation)
            val percent = OperationFactory.create(OperationFactory.PERCENT_ID, buffer.get(), binary.a)
            percent?.let {
                binary.b = it.result()
            }
        } else {
            operation = OperationFactory.create(OperationFactory.PERCENT_ID, buffer.get(), 1.0)
        }
    }

    //STATE
    data class State(
        val buffer: Double? = null,
        val memory: Double? = null,
        val operation: Operation? = null
    )
}