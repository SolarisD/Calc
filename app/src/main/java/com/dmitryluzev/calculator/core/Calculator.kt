package com.dmitryluzev.calculator.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.dmitryluzev.calculator.core.operations.Operation
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

    val bufferDisplay: LiveData<String> = Transformations.map(buffer.out){ it?.toString() ?: "0" }
    val memoryDisplay: LiveData<String> = Transformations.map(memory.out){ if (it.isNullOrEmpty()) "" else "M: $it" }
    val operationDisplay: LiveData<List<Operation>> = alu.out
    private var onResultReadyListener: ((Operation)->Unit)? = null
    var initialized = false
        private set
    init {
        alu.setOnResultReadyListener { buffer.setValue(it.result!!); onResultReadyListener?.invoke(it) }
    }
    fun getState() = State(buffer.getValue(), memory.getValue(), alu.current, alu.complete, alu.prev)
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
        if (alu.current != null) alu.setValue(buffer.getValue())
        else alu.repeat()
    }
    fun operation(op: Operation) {
        if (alu.current != null)
            if (buffer.clearRequest) alu.change(op)
            else alu.setValue(buffer.getValue())
        alu.setOperation(op, buffer.getValue())
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