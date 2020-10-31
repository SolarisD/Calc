package com.dmitryluzev.calculator.core

import androidx.lifecycle.MutableLiveData
import com.dmitryluzev.calculator.operations.BinaryOperation
import com.dmitryluzev.calculator.operations.Operation
import com.dmitryluzev.calculator.operations.Operations

class Alu constructor(){
    val out: MutableLiveData<List<Operation>> = MutableLiveData()
    var current: Operation? = null
        private set
    var complete: Operation? = null
        private set
    var prev: Operation? = null
        private set
    private var onResultReadyListener: ((Operation)->Unit)? = null

    fun setState(current: Operation? = null, complete: Operation? = null, prev: Operation? = null){
        this.current = current; this.complete = complete; this.prev = prev
        post()
    }
    fun clear(){
        current = null
        complete = null
        prev = null
        post()
    }
    fun setOperation(operation: Operation, value: Value){
        if (current == null){
            if (operation is BinaryOperation){
                current = operation
                current!!.a = value
            } else {
                operation.a = value
                current = operation
                push()
                onResultReadyListener?.invoke(complete!!)
            }
        }
        post()
    }
    fun repeat(){
        complete?.let {
            current = it
            current!!.a = it.result
            push()
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
            (current as BinaryOperation).apply { b = value }
            push()
            onResultReadyListener?.invoke(complete!!)
        }
        post()
    }
    fun setPercent(value: Value){
        if(current is BinaryOperation){
            (current as BinaryOperation).apply { b = value * Value(0.01) * current!!.a!! }
            push()
            onResultReadyListener?.invoke(complete!!)
        } else {
            val ret = Operations.Multiply()
            ret.a = Value(1.0)
            ret.b = Value(0.01) * value
            current = ret
            push()
            onResultReadyListener?.invoke(complete!!)
        }
        post()
    }
    fun setOnResultReadyListener(listener:(Operation) -> Unit){
        onResultReadyListener = listener
    }

    private fun push(){
        prev = complete
        complete = current
        current = null
    }

    private fun post(){
        val ret: MutableList<Operation> = mutableListOf()
        current?.let { ret.add(it) }
        complete?.let { ret.add(it) }
        prev?.let { if (ret.size < 2) ret.add(it) }
        out.postValue(ret)
    }


}