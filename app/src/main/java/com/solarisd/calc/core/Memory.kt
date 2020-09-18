package com.solarisd.calc.core

import androidx.lifecycle.MutableLiveData

class Memory {
    val out: MutableLiveData<String> = MutableLiveData()
    var data: Double? = null
        private set(value) {
            field = value
            out.postValue(value?.toDisplayString())
        }
    init {
        data = AppManager.restoreMemory()
    }
    fun clear(){
        data = null
        AppManager.saveMemory(null)
    }
    fun pls(value: Double){
        data = if(data == null){
            value
        } else{
            data!! + value
        }
        AppManager.saveMemory(data)
    }
    fun mns(value: Double){
        data = if(data == null){
            -value
        } else{
            data!! - value
        }
        AppManager.saveMemory(data)
    }
}