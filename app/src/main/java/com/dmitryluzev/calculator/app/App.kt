package com.dmitryluzev.calculator.app

import android.app.Application
import com.dmitryluzev.calculator.di.components.AppComponent
import com.dmitryluzev.calculator.di.components.DaggerAppComponent

class App: Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }
}