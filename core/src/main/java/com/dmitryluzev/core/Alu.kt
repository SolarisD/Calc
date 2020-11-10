package com.dmitryluzev.core

import androidx.lifecycle.MutableLiveData
import com.dmitryluzev.core.operations.Multiply
import com.dmitryluzev.core.operations.OperationFactory
import com.dmitryluzev.core.operations.base.BinaryOperation
import com.dmitryluzev.core.operations.base.Operation
import com.dmitryluzev.core.operations.base.UnaryOperation
import com.dmitryluzev.core.values.Value

class Alu constructor(){
    val out = MutableLiveData<Operation>()
    var operation: Operation? = null
        private set
    private var onResultReadyListener: ((Operation)->Unit)? = null

    fun setState(current: Operation? = null, complete: Operation? = null, prev: Operation? = null){
        operation = current
        post()
    }
    fun clear(){
        operation = null
        post()
    }
    fun setOperation(id: String, value: Value){
        OperationFactory.create(id, value)?.let {
            it.a = value
            operation = it
            if (it is UnaryOperation) {
                onResultReadyListener?.invoke(operation!!)
            }
            post()
        }

    }
    fun changeOperation(id: String){
        OperationFactory.create(id)?.let { newOp->
            if (operation is BinaryOperation){
                operation?.let {
                    newOp.a = it.a
                    operation = newOp
                }
            }
        }
        post()
    }
    fun setValue(value: Value){
        if(operation is BinaryOperation){
            (operation as BinaryOperation).apply { b = value }
            onResultReadyListener?.invoke(operation!!)
        }
        post()
    }
    fun repeat(){
        operation?.let {
            val op = OperationFactory.copy(it)
            op.a = it.result
            operation = op
            onResultReadyListener?.invoke(operation!!)
        }
        post()
    }
    fun setPercent(value: Value){
        if(operation is BinaryOperation){
            (operation as BinaryOperation).apply { b = value * Value(0.01) * operation!!.a!! }
            onResultReadyListener?.invoke(operation!!)
        } else {
            val ret = Multiply()
            ret.a = Value(1.0)
            ret.b = Value(0.01) * value
            operation = ret
            onResultReadyListener?.invoke(operation!!)
        }
        post()
    }
    fun setOnResultReadyListener(listener:(Operation) -> Unit){
        onResultReadyListener = listener
    }

    private fun post(){
        operation?.let {
            if (it.result == null) {
                out.value = operation
                return
            }
        }
        out.value = null
    }
}