package com.dmitryluzev.calculator.app

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.dmitryluzev.calculator.R
import com.dmitryluzev.core.Calculator
import com.dmitryluzev.core.values.toOperation
import com.dmitryluzev.core.values.toValue
import java.util.*

class Pref private constructor(private val application: Application): SharedPreferences.OnSharedPreferenceChangeListener {
    companion object{
        const val BUFFER_STATE_KEY = "buffer_state"
        const val MEMORY_STATE_KEY = "memory_state"
        const val ALU_OP_STATE_KEY = "alu_state"
        const val DISPLAY_HISTORY_DATE_KEY = "display_history_date"

        private var instance: Pref? = null
        fun getInstance(application: Application): Pref {
            if (instance == null) {
                instance = Pref(application)
            }
            return instance!!
        }
    }
    private val pref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(application)
    val liveDark = MutableLiveData(false)

    var haptic: Boolean
        private set
    var sound: Boolean
        private set

    init {
        liveDark.value = pref.getBoolean(application.getString(R.string.pref_dark_theme_key), false)
        haptic = pref.getBoolean(application.getString(R.string.pref_haptic_buttons_key), false)
        sound = pref.getBoolean(application.getString(R.string.pref_sound_buttons_key), false)
        setAppThemeMode(liveDark.value!!)
        pref.registerOnSharedPreferenceChangeListener(this)
    }
    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        when(p1){
            application.getString(R.string.pref_haptic_buttons_key)->{
                haptic = pref.getBoolean(application.getString(R.string.pref_haptic_buttons_key), false)
            }
            application.getString(R.string.pref_sound_buttons_key)->{
                sound  = pref.getBoolean(application.getString(R.string.pref_sound_buttons_key), false)
            }
            application.getString(R.string.pref_dark_theme_key)->{
                liveDark.value = pref.getBoolean(application.getString(R.string.pref_dark_theme_key), false)
                setAppThemeMode(liveDark.value!!)
            }
        }
    }
    fun saveDark(value: Boolean){
        pref.edit()
            .putBoolean(application.getString(R.string.pref_dark_theme_key), value)
            .apply()
    }
    private fun setAppThemeMode(mode: Boolean){
        if(mode) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
    fun restoreState(): Calculator.State {
        val buffer = pref.getString(BUFFER_STATE_KEY, null)?.toValue()
        val memory = pref.getString(MEMORY_STATE_KEY, null)?.toValue()
        val alu = pref.getString(ALU_OP_STATE_KEY, null).toOperation()
        return Calculator.State(buffer, memory, alu)
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
        if (state.alu != null){
            pref.edit()
                .putString(ALU_OP_STATE_KEY, state.alu?.toStoreString())
                .apply()
        } else {
            pref.edit()
                .remove(ALU_OP_STATE_KEY)
                .apply()
        }
    }
    fun saveDisplayHistoryDate(date: Date){
        pref.edit()
            .putLong(DISPLAY_HISTORY_DATE_KEY, date.time)
            .apply()
    }
    fun restoreDisplayHistoryDate() = Date(pref.getLong(DISPLAY_HISTORY_DATE_KEY, System.currentTimeMillis()))
}