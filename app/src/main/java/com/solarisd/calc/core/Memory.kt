package com.solarisd.calc.core

import androidx.lifecycle.MutableLiveData
import java.math.BigDecimal

class Memory {
    val out: MutableLiveData<String> = MutableLiveData()
    var data: Double? = null
        private set(value) {
            field = value
            out.postValue(value?.toString())
        }
    fun clear(){
        data = null
    }
    fun pls(value: Double){
        data = if(data == null){
            value
        } else{
            data!! + value
        }
    }
    fun mns(value: Double){
        data = if(data == null){
            -value
        } else{
            data!! - value
        }
    }
}