package com.solarisd.calc.core

import androidx.lifecycle.MutableLiveData
import com.solarisd.calc.app.AppManager

class Memory(value: String? = null) {
    val out: MutableLiveData<String> = MutableLiveData()
    var data: Double? = null
        private set(value) {
            field = value
            out.postValue(value?.toString())
        }
    init {
        value?.let {
            data = it.toDouble()
        }
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