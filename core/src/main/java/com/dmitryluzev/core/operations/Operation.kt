package com.dmitryluzev.core.operations

import com.dmitryluzev.core.buffer.Converter

interface Operation {
    val tag: String
    override fun hashCode(): Int
    override fun equals(other: Any?): Boolean
    fun result(): Double?
    fun copy(): Operation
    fun repeat(): Operation
}

