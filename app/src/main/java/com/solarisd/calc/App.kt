package com.solarisd.calc

import android.app.Application
import com.solarisd.calc.core.AppManager

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