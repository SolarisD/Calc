package com.dmitryluzev.calculator.core

import androidx.lifecycle.MutableLiveData
import com.dmitryluzev.calculator.operations.BinaryOperation
import com.dmitryluzev.calculator.operations.Operation
import com.dmitryluzev.calculator.operations.Operations
import javax.inject.Inject

class Alu @Inject constructor(){
    val outCurrent: MutableLiveData<String> = MutableLiveData()
    val outComplete: MutableLiveData<String> = MutableLiveData()

    var current: Operation? = null
        private set
    var complete: Operation? = null
        private set
    private var onResultReadyListener: ((Operation)->Unit)? = null
    fun setState(current: Operation? = null, complete: Operation? = null){
        this.current = current; this.complete = complete
        post()
    }
    fun clear(){
        current = null
        complete = null
        post()
    }
    fun setOperation(operation: Operation, value: Value){
        if (current == null){
            if (operation is BinaryOperation){
                current = operation
                current!!.a = value
            } else {
                operation.a = value
                complete = operation
                onResultReadyListener?.invoke(complete!!)
            }
        }
        post()
    }
    fun repeat(){
        complete?.let {
            current = it
            current!!.a = it.result
            complete = current
            current = null
            onResultReadyListener?.invoke(complete!!)
        }
        post()
    }
    fun change(operation: Operation){
        if (current is BinaryOperation){
            current?.let {
                current = operation
                current!!.a = it.a
            }
        }
        post()
    }
    fun setValue(value: Value){
        if(current is BinaryOperation){
            (current as BinaryOperation).apply {
                b = value
                complete = this
                current = null
            }
            onResultReadyListener?.invoke(complete!!)
        }
        post()
    }
    fun setPercent(value: Value){
        if(current is BinaryOperation){
            (current as BinaryOperation).apply {
                b = value * Value(0.01) * current!!.a!!
                complete = this
                current = null
            }
            onResultReadyListener?.invoke(complete!!)
        } else {
            val ret = Operations.Multiply()
            ret.a = Value(1.0)
            ret.b = Value(0.01) * value
            complete = ret
            onResultReadyListener?.invoke(complete!!)
        }
        post()
    }
    fun setOnResultReadyListener(listener:(Operation) -> Unit){
        onResultReadyListener = listener
    }
    private fun post(){
        outComplete.postValue(if(complete == null) "" else complete.toString())
        outCurrent.postValue(if(current == null) "" else current.toString())
    }
}