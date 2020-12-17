package com.dmitryluzev.core

import com.dmitryluzev.core.buffer.Buffer
import com.dmitryluzev.core.buffer.BufferImpl
import com.dmitryluzev.core.buffer.Symbols
import com.dmitryluzev.core.operations.BinaryOperation
import com.dmitryluzev.core.operations.Operation
import com.dmitryluzev.core.operations.OperationFactory
import com.dmitryluzev.core.operations.UnaryOperation

class CalculatorImpl(state: State? = null){

    private val buffer: Buffer = BufferImpl()
    private var memory: Double? = null
    private var operation: Operation? = null

    private var bufferClearRequest = false
    private var onOperationComplete: ((Operation) -> Unit)? = null


    init {
        state?.let {
            it.buffer?.let { buffer.set(it) }
            memory = it.memory
            operation = it.operation
        }
    }
    fun setOnOperationComplete(callback: ((Operation) -> Unit)?){
        onOperationComplete = callback
    }
    fun get(): State {
        return State(buffer.get(), buffer.toString(), memory, operation)
    }
    fun clear() {
        buffer.clear()
        bufferClearRequest = false
        operation = null
    }
    //BUFFER
    fun setBuffer(double: Double){
        bufferClearRequest = false
        buffer.set(double)
    }
    fun clearBuffer() {
        buffer.clear()
        bufferClearRequest = false
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
    private fun operationComplete(op: Operation){
        buffer.set(op.result()!!)
        bufferClearRequest = true
        onOperationComplete?.invoke(op)
    }
    //MEMORY
    fun clearMem() {
        memory = null
    }
    fun addMem(){
        memory = if(memory == null){
            buffer.get()
        } else{
            memory!! + buffer.get()
        }
    }
    fun subMem(){
        memory = if(memory == null){
            -buffer.get()
        } else{
            memory!! - buffer.get()
        }
    }
    fun restoreMem(){
        memory?.let {
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
                    operationComplete(operation!!)
                } else {
                    operation = op.repeat()
                    operationComplete(operation!!)
                }
            }
            else -> {}
        }
    }
    fun operation(id: String) {
        val op = operation
        //If current binary is incomplete
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
                    operationComplete(operation!!)
                }
            }
        }
        //SET NEW
        operation = OperationFactory.create(id, buffer.get())
        bufferClearRequest = true
    }
    fun percent() {
        if(operation is BinaryOperation){
            val binary = (operation as BinaryOperation)
            val percent = OperationFactory.create(OperationFactory.PERCENT_ID, buffer.get(), binary.a)
            operationComplete(percent!!)
            binary.b = percent.result()
            operationComplete(operation!!)
        } else {
            operation = OperationFactory.create(OperationFactory.PERCENT_ID, buffer.get(), 1.0)
            operationComplete(operation!!)
        }
    }
}