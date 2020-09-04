package com.solarisd.calc.model

import android.content.SharedPreferences


private const val KEY_KEYBOARD_MODE = "key_keyboard_mode"
private const val KEY_VIBRO_MODE = "key_vibro_mode"

class CalcPrefs(private val sharedPrefs: SharedPreferences){
    fun setKeyboardMode(mode: String) {
        sharedPrefs.edit()
            .putString(KEY_KEYBOARD_MODE, mode)
            .apply()
    }
    fun getKeyboardMode(): String = sharedPrefs.getString(KEY_KEYBOARD_MODE, "DEF") ?: "DEF"
    fun setVibroMode(mode: Boolean){
        sharedPrefs.edit()
            .putBoolean(KEY_VIBRO_MODE, mode)
            .apply()
    }
    fun getVibroMode(): Boolean = sharedPrefs.getBoolean(KEY_VIBRO_MODE, false) ?: false
}