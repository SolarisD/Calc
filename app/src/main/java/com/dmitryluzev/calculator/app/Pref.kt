package com.dmitryluzev.calculator.app

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.dmitryluzev.calculator.R
import com.dmitryluzev.calculator.core.Calculator
import com.dmitryluzev.calculator.core.toOperation
import com.dmitryluzev.calculator.core.toValue

const val BUFFER_STATE_KEY = "buffer_state"
const val MEMORY_STATE_KEY = "memory_state"
const val CURRENT_OP_STATE_KEY = "current_op_state"
const val COMPLETE_OP_STATE_KEY = "complete_op_state"
const val PREV_OP_STATE_KEY = "prev_op_state"



class Pref private constructor(private val application: Application): SharedPreferences.OnSharedPreferenceChangeListener {
    companion object{
        private var instance: Pref? = null
        fun getInstance(application: Application): Pref {
            if (instance == null) {
                instance = Pref(application)
            }
            return instance!!
        }
    }


    private var pref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(application)
    private var nightTheme: Boolean = false
    var haptic: Boolean = false
        private set
    var sound: Boolean = false
        private set
    init {
        haptic = pref.getBoolean(application.getString(R.string.pref_haptic_buttons_key), false)
        sound = pref.getBoolean(application.getString(R.string.pref_sound_buttons_key), false)
        nightTheme = pref.getBoolean(application.getString(R.string.pref_dark_theme_key), false)
        if (nightTheme) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        pref.registerOnSharedPreferenceChangeListener(this)
    }
    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        when(p1){
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

    fun restoreState(): Calculator.State {
        val buffer = pref.getString(BUFFER_STATE_KEY, null)?.toValue()
        val memory = pref.getString(MEMORY_STATE_KEY, null)?.toValue()
        val current = pref.getString(CURRENT_OP_STATE_KEY, null).toOperation()
        val complete = pref.getString(COMPLETE_OP_STATE_KEY, null).toOperation()
        val prev = pref.getString(PREV_OP_STATE_KEY, null).toOperation()
        return Calculator.State(buffer, memory, current, complete, prev)
    }
    fun saveState(state: Calculator.State){
        //Buffer
        if (state.buffer != null){
            pref.edit()
                .putString(BUFFER_STATE_KEY, state.buffer.toString())
                .apply()
        } else {
            pref.edit()
                .remove(BUFFER_STATE_KEY)
                .apply()
        }
        //Memory
        if (state.memory != null){
            pref.edit()
                .putString(MEMORY_STATE_KEY, state.memory.toString())
                .apply()
        } else {
            pref.edit()
                .remove(MEMORY_STATE_KEY)
                .apply()
        }
        //Current operation
        if (state.current != null){
            pref.edit()
                .putString(CURRENT_OP_STATE_KEY, state.current.toStoreString())
                .apply()
        } else {
            pref.edit()
                .remove(CURRENT_OP_STATE_KEY)
                .apply()
        }
        //Complete operation
        if (state.complete != null){
            pref.edit()
                .putString(COMPLETE_OP_STATE_KEY, state.complete.toStoreString())
                .apply()
        } else {
            pref.edit()
                .remove(COMPLETE_OP_STATE_KEY)
                .apply()
        }
        //Prev operation
        if (state.prev != null){
            pref.edit()
                .putString(PREV_OP_STATE_KEY, state.prev.toStoreString())
                .apply()
        } else {
            pref.edit()
                .remove(PREV_OP_STATE_KEY)
                .apply()
        }
    }
}