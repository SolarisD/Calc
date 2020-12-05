package com.dmitryluzev.core

import androidx.lifecycle.MutableLiveData
import com.dmitryluzev.core.values.Value

class Memory constructor() {
    val out: MutableLiveData<Value> = MutableLiveData()
    private var value: Value? = null
    init {
        post()
    }
    fun clear(){
        value = null
        post()
    }
    fun add(value: Value){
        this.value = if(this.value == null){
            value
        } else{
            this.value!! + value
        }
        post()
    }
    fun sub(value: Value){
        this.value = if(this.value == null){
            -value
        } else{
            this.value!! - value
        }
        post()
    }
    fun getValue(): Value?{
        return value?.let {
            Value.getInstance().apply { from(it) }
        }
    }
    private fun post(){
        out.value = value
    }
}