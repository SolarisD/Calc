package com.solarisd.calc.core

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.solarisd.calc.R

object PrefManager: SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var app: Application
    private lateinit var pref: SharedPreferences

    private var nightTheme: Boolean = false
    var vibro: Boolean = false
        private set
    var sound: Boolean = false
        private set
    var keyboard: Boolean = false
        private set

    fun register(app: Application){
        this.app = app
        pref = PreferenceManager.getDefaultSharedPreferences(app)
        keyboard = pref.getBoolean(PrefManager.app.getString(R.string.extended_keyboard_key), false)
        vibro = pref.getBoolean(PrefManager.app.getString(R.string.vibration_buttons_key), false)
        sound = pref.getBoolean(PrefManager.app.getString(R.string.sound_buttons_key), false)
        nightTheme = pref.getBoolean(PrefManager.app.getString(R.string.night_theme_key), false)
        if (nightTheme) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        pref.registerOnSharedPreferenceChangeListener(this)
    }
    fun unregister(){
        PreferenceManager.getDefaultSharedPreferences(app).unregisterOnSharedPreferenceChangeListener(this)
    }
    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        when(p1){
            app.getString(R.string.extended_keyboard_key)->{
                keyboard = pref.getBoolean(app.getString(R.string.extended_keyboard_key), false)
            }
            app.getString(R.string.vibration_buttons_key)->{
                vibro = pref.getBoolean(app.getString(R.string.vibration_buttons_key), false)
            }
            app.getString(R.string.sound_buttons_key)->{
                sound = pref.getBoolean(app.getString(R.string.sound_buttons_key), false)
            }
            app.getString(R.string.night_theme_key)->{
                nightTheme = pref.getBoolean(app.getString(R.string.night_theme_key), false)
                if(nightTheme) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
    fun setString(key: String, value: String?){
        pref.edit()
            .putString(key, value)
            .apply()
    }
    fun getString(key: String): String?{
        return  pref.getString(key, null)
    }
}