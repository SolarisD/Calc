package com.dmitryluzev.calculator.core

import androidx.lifecycle.MutableLiveData

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
        return value?.copy()
    }
    private fun post(){
        out.value = value
        /*if (value != null){
            out.postValue(value)
        } else {
            out.postValue(null)
        }*/
    }
}