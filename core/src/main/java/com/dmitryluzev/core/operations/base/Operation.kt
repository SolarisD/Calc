package com.dmitryluzev.core.operations.base

import com.dmitryluzev.core.values.Value

interface Operation {
    var a: Value?
    val result: Value?
}