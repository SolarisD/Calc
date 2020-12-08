package com.dmitryluzev.core

import androidx.lifecycle.MutableLiveData

class Pipeline{
    val out = MutableLiveData<Operation>()
    var operation: Operation? = null
        private set

    private var onResultReadyListener: ((Operation) -> Unit)? = null

    fun set(operation: Operation){
        this.operation = operation
        post()
    }
    fun clear(){
        operation = null
        post()
    }
    fun clearIfComplete(){
        operation?.result()?.let {
            clear()
        }
    }
    fun setOperation(id: String, value: Double){
        operation = OperationFactory.create(id, value)
        if (operation is UnaryOperation) onResultReadyListener?.invoke(operation!!)
        post()
    }
    fun changeOperation(id: String) {
        when (val old = operation) {
            is BinaryOperation -> {
                val new = OperationFactory.create(id)
                if (new is BinaryOperation) {
                    new.a = old.a
                    operation = new
                }
            }
        }
        post()
    }
    fun repeat(){
        operation = operation?.repeat()
        operation?.let { onResultReadyListener?.invoke(it) }
        post()
    }
    fun setValue(value: Double){
        if(operation is BinaryOperation){
            (operation as BinaryOperation).apply { b = value }
            onResultReadyListener?.invoke(operation!!)
        }
        post()
    }
    fun setPercent(value: Double){
        if(operation is BinaryOperation){
            (operation as BinaryOperation).apply { b = value; percentage = true }
        } else {
            operation = OperationFactory.create(OperationFactory.DIVIDE_ID, value, 100.0, false)
        }
        operation?.let { onResultReadyListener?.invoke(it) }
        post()
    }
    fun setOnResultReadyListener(listener:(Operation) -> Unit){
        onResultReadyListener = listener
    }
    private fun post(){
        operation?.let {
            if (it.result() == null) {
                out.value = operation
                return
            }
        }
        out.value = null
    }
}