package com.dmitryluzev.calculator.app

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.dmitryluzev.calculator.R
import com.dmitryluzev.core.Calculator
import com.dmitryluzev.core.values.toOperation
import com.dmitryluzev.core.values.toValue
import java.util.*




class PrefManager private constructor(private val application: Application): SharedPreferences.OnSharedPreferenceChangeListener {
    companion object{
        const val BUFFER_STATE_KEY = "buffer_state"
        const val MEMORY_STATE_KEY = "memory_state"
        const val ALU_OP_STATE_KEY = "alu_state"
        const val DISPLAY_HISTORY_DATE_KEY = "display_history_date"

        private var instance: PrefManager? = null
        fun getInstance(application: Application): PrefManager {
            if (instance == null) {
                instance = PrefManager(application)
            }
            return instance!!
        }
    }
    data class Pref(var dark: Boolean = false,
                    var haptic: Boolean = false,
                    var sound: Boolean = false)

    private val sharedPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(application)
    private val _livePref: MutableLiveData<Pref>
    val livePref: LiveData<Pref>
    private val pref = Pref()
    init {
        pref.dark = sharedPref.getBoolean(application.getString(R.string.pref_dark_theme_key), false)
        pref.haptic = sharedPref.getBoolean(application.getString(R.string.pref_haptic_buttons_key), false)
        pref.sound = sharedPref.getBoolean(application.getString(R.string.pref_sound_buttons_key), false)
        _livePref = MutableLiveData(pref)
        livePref = _livePref
        setAppThemeMode(pref.dark)
        sharedPref.registerOnSharedPreferenceChangeListener(this)
    }
    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        when(p1){
            application.getString(R.string.pref_dark_theme_key)->{
                pref.dark = sharedPref.getBoolean(application.getString(R.string.pref_dark_theme_key), false)
                setAppThemeMode(pref.dark)
            }
            application.getString(R.string.pref_haptic_buttons_key)->{
                pref.haptic = sharedPref.getBoolean(application.getString(R.string.pref_haptic_buttons_key), false)
            }
            application.getString(R.string.pref_sound_buttons_key)->{
                pref.sound  = sharedPref.getBoolean(application.getString(R.string.pref_sound_buttons_key), false)
            }

        }
        _livePref.value = pref
    }
    fun saveDark(value: Boolean){
        sharedPref.edit()
            .putBoolean(application.getString(R.string.pref_dark_theme_key), value)
            .apply()
    }
    fun saveHaptic(value: Boolean){
        sharedPref.edit()
            .putBoolean(application.getString(R.string.pref_haptic_buttons_key), value)
            .apply()
    }
    fun saveSound(value: Boolean){
        sharedPref.edit()
            .putBoolean(application.getString(R.string.pref_sound_buttons_key), value)
            .apply()
    }
    private fun setAppThemeMode(mode: Boolean){
        if(mode) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
    fun restoreState(): Calculator.State {
        val buffer = sharedPref.getString(BUFFER_STATE_KEY, null)?.toValue()
        val memory = sharedPref.getString(MEMORY_STATE_KEY, null)?.toValue()
        val alu = sharedPref.getString(ALU_OP_STATE_KEY, null).toOperation()
        return Calculator.State(buffer, memory, alu)
    }
    fun saveState(state: Calculator.State){
        //Buffer
        if (state.buffer != null){
            sharedPref.edit()
                .putString(BUFFER_STATE_KEY, state.buffer.toString())
                .apply()
        } else {
            sharedPref.edit()
                .remove(BUFFER_STATE_KEY)
                .apply()
        }
        //Memory
        if (state.memory != null){
            sharedPref.edit()
                .putString(MEMORY_STATE_KEY, state.memory.toString())
                .apply()
        } else {
            sharedPref.edit()
                .remove(MEMORY_STATE_KEY)
                .apply()
        }
        //Current operation
        if (state.alu != null){
            sharedPref.edit()
                .putString(ALU_OP_STATE_KEY, state.alu?.toStoreString())
                .apply()
        } else {
            sharedPref.edit()
                .remove(ALU_OP_STATE_KEY)
                .apply()
        }
    }
    fun saveDisplayHistoryDate(date: Date){
        sharedPref.edit()
            .putLong(DISPLAY_HISTORY_DATE_KEY, date.time)
            .apply()
    }
    fun restoreDisplayHistoryDate() = Date(sharedPref.getLong(DISPLAY_HISTORY_DATE_KEY, System.currentTimeMillis()))
}