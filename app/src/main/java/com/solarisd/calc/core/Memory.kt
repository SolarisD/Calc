package com.solarisd.calc.core

import androidx.lifecycle.MutableLiveData
import java.math.BigDecimal

class Memory {
    val out: MutableLiveData<String> = MutableLiveData()
    var data: BigDecimal? = null
        private set(value) {
            field = value
            out.postValue(value?.toDisplayString())
        }
    fun clear(){
        data = null
    }
    fun pls(value: BigDecimal){
        data = if(data == null){
            value
        } else{
            data!!.add(value)
        }
    }
    fun mns(value: BigDecimal){
        data = if(data == null){
            -value
        } else{
            data!!.subtract(value)
        }
    }
}