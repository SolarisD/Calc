package com.dmitryluzev.calculator.app

import android.app.Application

class App: Application() {
    override fun onCreate() {
        //Preload manager to init theme
       PrefManager.getInstance(this)
        super.onCreate()
    }
}