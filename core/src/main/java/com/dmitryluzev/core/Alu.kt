package com.dmitryluzev.core

import androidx.lifecycle.MutableLiveData
import com.dmitryluzev.core.operations.Multiply
import com.dmitryluzev.core.operations.OperationFactory
import com.dmitryluzev.core.operations.base.BinaryOperation
import com.dmitryluzev.core.operations.base.Operation
import com.dmitryluzev.core.operations.base.UnaryOperation
import com.dmitryluzev.core.values.Value

class Alu constructor(){
    val outCurrent = MutableLiveData<Operation>()
    val outComplete = MutableLiveData<Operation>()

    var current: Operation? = null
        private set
    var complete: Operation? = null
        private set
    private var onResultReadyListener: ((Operation)->Unit)? = null

    fun setState(current: Operation? = null, complete: Operation? = null, prev: Operation? = null){
        this.current = current; this.complete = complete;
        post()
    }
    fun clear(){
        current = null
        complete = null
        post()
    }
    fun setOperation(id: String, value: Value){
        OperationFactory.create(id, value)?.let {
            complete = current
            it.a = value
            current = it
            if (it is UnaryOperation) {
                onResultReadyListener?.invoke(current!!)
            }
            post()
        }

    }
    fun changeOperation(id: String){
        OperationFactory.create(id)?.let { newOp->
            if (current is BinaryOperation){
                current?.let {
                    newOp.a = it.a
                    current = newOp
                }
            }
        }
        post()
    }
    fun setValue(value: Value){
        if(current is BinaryOperation){
            (current as BinaryOperation).apply { b = value }
            onResultReadyListener?.invoke(current!!)
        }
        post()
    }
    fun repeat(){
        current?.let {
            val op = OperationFactory.copy(it)
            op.a = it.result
            complete = current
            current = op
            onResultReadyListener?.invoke(current!!)
        }
        post()
    }
    fun setPercent(value: Value){
        if(current is BinaryOperation){
            (current as BinaryOperation).apply { b = value * Value(0.01) * current!!.a!! }
            onResultReadyListener?.invoke(current!!)
        } else {
            val ret = Multiply()
            ret.a = Value(1.0)
            ret.b = Value(0.01) * value
            current = ret
            onResultReadyListener?.invoke(current!!)
        }
        post()
    }
    fun setOnResultReadyListener(listener:(Operation) -> Unit){
        onResultReadyListener = listener
    }
    private fun post(){
        outCurrent.value = current
        outComplete.value = complete
    }

}