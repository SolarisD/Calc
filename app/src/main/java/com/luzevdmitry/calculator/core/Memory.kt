package com.luzevdmitry.calculator.core

import androidx.lifecycle.MutableLiveData
import com.luzevdmitry.calculator.app.AppManager

class Memory(value: Value? = null) {
    val out: MutableLiveData<String> = MutableLiveData()
    private var value: Value? = value
    init {
        post()
    }
    fun clear(){
        value = null
        post()
    }
    fun pls(value: Value){
        this.value = if(this.value == null){
            value
        } else{
            this.value!! + value
        }
        post()
    }
    fun mns(value: Value){
        this.value = if(this.value == null){
            -value
        } else{
            this.value!! - value
        }
        post()
    }
    fun get(): Value{
        return value?.copy() ?: Value()
    }
    private fun post(){
        if (value != null){
            out.postValue(value.toString())
        } else {
            out.postValue(null)
        }
        AppManager.saveMemory(value)
    }
}