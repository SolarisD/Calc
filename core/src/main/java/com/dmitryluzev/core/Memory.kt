package com.dmitryluzev.core

import androidx.lifecycle.MutableLiveData

class Memory() {
    val get: Double?
        get() = value
    private var value: Double? = null

    fun clear(){
        value = null
    }
    fun add(value: Double){
        this.value = if(this.value == null){
            value
        } else{
            this.value!! + value
        }
    }
    fun sub(value: Double){
        this.value = if(this.value == null){
            -value
        } else{
            this.value!! - value
        }
    }
}