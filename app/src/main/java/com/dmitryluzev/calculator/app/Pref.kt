package com.dmitryluzev.calculator.app

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.dmitryluzev.calculator.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Pref @Inject constructor(private val application: Application): SharedPreferences.OnSharedPreferenceChangeListener {
    private var pref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(application)
    private var nightTheme: Boolean = false
    var haptic: Boolean = false
        private set
    var sound: Boolean = false
        private set
    var keyboard: Boolean = false
        private set
    init {
        keyboard = pref.getBoolean(application.getString(R.string.extended_keyboard_key), false)
        haptic = pref.getBoolean(application.getString(R.string.pref_haptic_buttons_key), false)
        sound = pref.getBoolean(application.getString(R.string.pref_sound_buttons_key), false)
        nightTheme = pref.getBoolean(application.getString(R.string.pref_dark_theme_key), false)
        if (nightTheme) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        pref.registerOnSharedPreferenceChangeListener(this)
    }
    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        when(p1){
            application.getString(R.string.extended_keyboard_key)->{
                keyboard = false //pref.getBoolean(app.getString(R.string.extended_keyboard_key), false)
            }
            application.getString(R.string.pref_haptic_buttons_key)->{
                haptic = pref.getBoolean(application.getString(R.string.pref_haptic_buttons_key), false)
            }
            application.getString(R.string.pref_sound_buttons_key)->{
                sound = pref.getBoolean(application.getString(R.string.pref_sound_buttons_key), false)
            }
            application.getString(R.string.pref_dark_theme_key)->{
                nightTheme = pref.getBoolean(application.getString(R.string.pref_dark_theme_key), false)
                if(nightTheme) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}