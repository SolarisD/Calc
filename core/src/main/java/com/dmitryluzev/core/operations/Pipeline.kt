package com.dmitryluzev.core.operations

import androidx.lifecycle.MutableLiveData
import com.dmitryluzev.core.BinaryOperation
import com.dmitryluzev.core.Operation
import com.dmitryluzev.core.OperationFactory
import com.dmitryluzev.core.UnaryOperation

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
            val binary = (operation as BinaryOperation)
            val percent = OperationFactory.create(OperationFactory.PERCENT_ID, value, binary.a)
            percent?.let {
                onResultReadyListener?.invoke(it)
                binary.b = it.result()
            }
        } else {
            operation = OperationFactory.create(OperationFactory.PERCENT_ID, value, 1.0)
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