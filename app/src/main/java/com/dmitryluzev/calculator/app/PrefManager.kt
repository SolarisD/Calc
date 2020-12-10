package com.dmitryluzev.calculator.app

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.dmitryluzev.calculator.R
import com.dmitryluzev.core.calculator.Calculator
import com.dmitryluzev.core.buffer.Converter
import com.dmitryluzev.core.calculator.State
import com.dmitryluzev.core.operations.OperationFactory
import java.util.*


class PrefManager private constructor(private val application: Application): SharedPreferences.OnSharedPreferenceChangeListener {
    companion object{
        const val BUFFER_STATE_KEY = "buffer_state"
        const val MEMORY_STATE_KEY = "memory_state"
        const val OPERATION_STATE_KEY = "operation_state"
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
    fun restoreState(): State {

        val buffer = Converter.stringToDouble(sharedPref.getString(BUFFER_STATE_KEY, null))
        val memory = Converter.stringToDouble(sharedPref.getString(MEMORY_STATE_KEY, null))
        val pipeline = OperationFactory.fromStoreString(sharedPref.getString(OPERATION_STATE_KEY, null))
        return State(buffer, memory, pipeline)
    }
    fun saveState(state: State){
        //BufferImpl
        if (state.buffer != null){
            sharedPref.edit()
                .putString(BUFFER_STATE_KEY, state.buffer.toString())
                .apply()
        } else {
            sharedPref.edit()
                .remove(BUFFER_STATE_KEY)
                .apply()
        }
        //MemoryImpl
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
        if (state.operation != null){
            sharedPref.edit()
                .putString(OPERATION_STATE_KEY, OperationFactory.toStoreString(state.operation))
                .apply()
        } else {
            sharedPref.edit()
                .remove(OPERATION_STATE_KEY)
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