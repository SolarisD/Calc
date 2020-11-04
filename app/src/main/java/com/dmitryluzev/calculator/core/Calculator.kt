package com.dmitryluzev.calculator.core

import androidx.lifecycle.LiveData
import com.dmitryluzev.calculator.core.operations.OperationFactory
import com.dmitryluzev.calculator.core.operations.base.Operation
import java.lang.Exception


class Calculator private constructor(){
    companion object{
        private var INSTANCE: Calculator? = null
        fun getInstance(): Calculator{
            var instance = INSTANCE
            if (instance == null){
                instance = Calculator()
                INSTANCE = instance
            }
            return instance
        }
    }
    private val buffer: Buffer = Buffer()
    private val memory: Memory = Memory()
    private val alu: Alu = Alu()

    val bufferOut: LiveData<Value> = buffer.out
    val memoryDisplay: LiveData<Value> = memory.out
    val aluCurrent: LiveData<Operation> = alu.outCurrent
    val aluComplete: LiveData<Operation> = alu.outComplete

    private var onResultReadyListener: ((Operation)->Unit)? = null
    var initialized = false
        private set
    init {
        alu.setOnResultReadyListener { buffer.setValue(it.result!!); onResultReadyListener?.invoke(it) }
    }
    fun getState() = State(buffer.getValue(), memory.getValue(), alu.current, alu.complete, null)
    fun setState(state: State){
        state.buffer?.let { buffer.setValue(it) }
        state.memory?.let { memory.add(it) }
        alu.setState(state.current, state.complete, state.prev)
        initialized = true
    }
    fun setOnResultReadyListener(listener:(Operation) -> Unit){
        onResultReadyListener = listener
    }
    fun clear() { buffer.clear(); alu.clear() }
    fun symbol(symbol: Symbols) = buffer.symbol(symbol)
    fun negative() = buffer.negative()
    fun backspace() = buffer.backspace()
    fun result() {
        alu.current?.let {
            if (it.result == null) alu.setValue(buffer.getValue())
            else alu.repeat()
        }
    }
    fun operation(id: OperationFactory.ID) {
        alu.current?.let {
            if (it.result == null){
                if (buffer.clearRequest) alu.changeOperation(id)
                else alu.setValue(buffer.getValue())
            }
        }
        alu.setOperation(id, buffer.getValue())
    }
    fun percent() {
        alu.setPercent(buffer.getValue())
    }
    fun memoryClear() = memory.clear()
    fun memoryAdd() = memory.add(buffer.getValue())
    fun memorySubtract() = memory.sub(buffer.getValue())
    fun memoryRestore(){
        memory.getValue()?.let {
            buffer.setValue(it)
        }
    }
    fun pasteFromClipboard(value: String): String?{
        try {
            value.toValue()?.let {
                buffer.setValue(it)
                return it.toString()
            }
            return null
        } catch (e: Exception){
            return null
        }
    }
    fun clearBuffer(){
        buffer.clear()
    }

    data class State(
        val buffer: Value? = null,
        val memory: Value? = null,
        val current: Operation? = null,
        val complete: Operation? = null,
        val prev: Operation? = null
    )
}