package com.dmitryluzev.core

import androidx.lifecycle.MutableLiveData

class Memory() {
    val out: MutableLiveData<String> = MutableLiveData()
    private var value: Double? = null
    init {
        post()
    }
    fun clear(){
        value = null
        post()
    }
    fun add(value: Double){
        this.value = if(this.value == null){
            value
        } else{
            this.value!! + value
        }
        post()
    }
    fun sub(value: Double){
        this.value = if(this.value == null){
            -value
        } else{
            this.value!! - value
        }
        post()
    }
    fun getValue(): Double?{
        return value
    }
    private fun post(){
        out.value = Converter.doubleToString(value)
    }
}