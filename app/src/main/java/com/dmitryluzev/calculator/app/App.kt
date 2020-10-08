package com.dmitryluzev.calculator.app

import android.app.Application

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        AppManager.register(this)
    }

    override fun onTerminate() {
        AppManager.unregister()
        super.onTerminate()
    }
}