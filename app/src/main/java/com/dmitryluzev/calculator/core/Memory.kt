package com.dmitryluzev.calculator.core

import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class Memory @Inject constructor() {
    val out: MutableLiveData<String> = MutableLiveData()
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
        if (value != null){
            out.postValue(value.toString())
        } else {
            out.postValue(null)
        }
    }
}