package com.dmitryluzev.core.memory

class MemoryImpl(): Memory {
    private var value: Double? = null

    override fun clear(){
        value = null
    }
    override fun add(value: Double){
        this.value = if(this.value == null){
            value
        } else{
            this.value!! + value
        }
    }
    override fun sub(value: Double){
        this.value = if(this.value == null){
            -value
        } else{
            this.value!! - value
        }
    }

    override fun get(): Double? {
        return value
    }
}