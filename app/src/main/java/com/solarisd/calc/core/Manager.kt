package com.solarisd.calc.core

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

object Manager {
    private var pref: SharedPreferences? = null
    fun register(app: Application){
        if (pref == null) {
            pref = PreferenceManager.getDefaultSharedPreferences(app)
            pref?.registerOnSharedPreferenceChangeListener(::onChangeListener)
        }
    }
    fun unregister(){
        pref?.unregisterOnSharedPreferenceChangeListener(::onChangeListener)
    }
    fun onChangeListener(sharedPreferences: SharedPreferences, s: String){

    }
}