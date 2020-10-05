package com.solarisd.calc.core

import androidx.lifecycle.MutableLiveData
import com.solarisd.calc.app.AppManager

class Memory(value: String? = null) {

    val out: MutableLiveData<String> = MutableLiveData()

    private var value: Value? = null
        private set(value) {
            field = value
            if (value != null){
                out.postValue(value.toString())
            } else {
                out.postValue(null)
            }
        }

    init {
        value?.let {
            this.value = it.toValue()
        }
    }
    fun clear(){
        value = null
        AppManager.saveMemory(null)
    }
    fun pls(value: Value){
        this.value = if(this.value == null){
            value
        } else{
            this.value!! + value
        }
        AppManager.saveMemory(this.value)
    }
    fun mns(value: Value){
        this.value = if(this.value == null){
            -value
        } else{
            this.value!! - value
        }
        AppManager.saveMemory(this.value)
    }
    fun get(): Value{
        return value?.copy() ?: Value()
    }
}