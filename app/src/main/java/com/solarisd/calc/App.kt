package com.solarisd.calc

import android.app.Application
import com.solarisd.calc.core.PrefManager

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        PrefManager.register(this)
    }

    override fun onTerminate() {
        PrefManager.unregister()
        super.onTerminate()
    }
}