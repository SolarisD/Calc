package com.dmitryluzev.core

import androidx.lifecycle.LiveData

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
    private val buffer = Buffer()
    private val memory = Memory()
    private val pipeline = Pipeline()

    val bufferOut: LiveData<String> = buffer.out
    val memoryDisplay: LiveData<String> = memory.out
    val pipelineOut: LiveData<Operation> = pipeline.out

    private var onResultReadyListener: ((Operation)->Unit)? = null
    var initialized = false
        private set
    init {
        pipeline.setOnResultReadyListener { buffer.set(it.result()!!); onResultReadyListener?.invoke(it) }
    }
    fun getState() = State(buffer.get(), memory.getValue(), pipeline.operation)
    fun setState(state: State){
        state.buffer?.let { buffer.set(it) }
        state.memory?.let { memory.add(it) }
        state.pipeline?.let { pipeline.set(it) }
        initialized = true
    }
    fun setOnResultReadyListener(listener:(Operation) -> Unit){
        onResultReadyListener = listener
    }
    fun clear() { buffer.clear(); pipeline.clear() }
    fun symbol(symbol: Buffer.Symbols) {
        pipeline.clearIfComplete()
        buffer.symbol(symbol)
    }
    fun negative(){
        pipeline.clearIfComplete()
        buffer.negative()
    }
    fun backspace() {
        pipeline.clearIfComplete()
        buffer.backspace()
    }
    fun result() {
        pipeline.operation?.let {
            if (it.result() == null) pipeline.setValue(buffer.get())
            else pipeline.repeat()
        }
    }
    fun operation(id: String) {
        pipeline.operation?.let {
            if (it.result() == null){
                if (buffer.clearRequest) {pipeline.changeOperation(id); return}
                else pipeline.setValue(buffer.get())
            }
        }
        pipeline.setOperation(id, buffer.get())
    }
    fun percent() {
        pipeline.setPercent(buffer.get())
    }
    fun memoryClear() = memory.clear()
    fun memoryAdd() = memory.add(buffer.get())
    fun memorySubtract() = memory.sub(buffer.get())
    fun memoryRestore(){
        memory.getValue()?.let {
            buffer.set(it)
        }
    }
    fun pasteFromClipboard(value: String): String?{
        try {
            Converter.stringToDouble(value)?.let {
                buffer.set(it)
                return value
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
        val buffer: Double? = null,
        val memory: Double? = null,
        val pipeline: Operation? = null
    )
}