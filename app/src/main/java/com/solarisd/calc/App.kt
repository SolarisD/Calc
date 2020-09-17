package com.solarisd.calc

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        setThemeMode()
    }
    private fun setThemeMode(){
        val night = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("dark_theme", false)
        if (night) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}