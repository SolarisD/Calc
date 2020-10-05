package com.solarisd.calc.app

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.solarisd.calc.R
import com.solarisd.calc.core.*
import com.solarisd.calc.model.State

object AppManager: SharedPreferences.OnSharedPreferenceChangeListener {
    private const val BUFFER_STATE_KEY = "buffer_state"
    private const val BUFFER_CLEAR_REQUEST_KEY = "buffer_clear_request"
    private const val MEMORY_STATE_KEY = "memory_state"
    private const val BINARY_STATE_KEY = "binary_state"
    private const val LAST_STATE_KEY = "last_state"
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
        AppManager.app = app
        pref = PreferenceManager.getDefaultSharedPreferences(app)
        keyboard = pref.getBoolean(AppManager.app.getString(R.string.extended_keyboard_key), false)
        vibro = pref.getBoolean(AppManager.app.getString(R.string.vibration_buttons_key), false)
        sound = pref.getBoolean(AppManager.app.getString(R.string.sound_buttons_key), false)
        nightTheme = pref.getBoolean(AppManager.app.getString(R.string.night_theme_key), false)
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
                keyboard = false //pref.getBoolean(app.getString(R.string.extended_keyboard_key), false)
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

    //region Save/Restore core state TODO: rewrite to ROOM or something else
    fun saveBuffer(value: Value?){
        if (value != null){
            pref.edit()
                .putString(BUFFER_STATE_KEY, value.toString())
                .apply()
        } else {
            pref.edit()
                .remove(BUFFER_STATE_KEY)
                .apply()
        }
    }
    fun saveBufferClearRequest(value: Boolean){
        pref.edit()
            .putBoolean(BUFFER_CLEAR_REQUEST_KEY, value)
            .apply()
    }
    fun saveMemory(value: Value?){
        if (value != null){
            pref.edit()
                .putString(MEMORY_STATE_KEY, value.toString())
                .apply()
        } else {
            pref.edit()
                .remove(MEMORY_STATE_KEY)
                .apply()
        }
    }
    fun saveBinary(value: Operation?){
        val storeString = operationToStoreString(value)
        if (storeString != null){
            pref.edit()
                .putString(BINARY_STATE_KEY, storeString)
                .apply()
        } else {
            pref.edit()
                .remove(BINARY_STATE_KEY)
                .apply()
        }
    }
    fun saveLast(value: Operation?){
        val storeString = operationToStoreString(value)
        if (storeString != null){
            pref.edit()
                .putString(LAST_STATE_KEY, storeString)
                .apply()
        } else {
            pref.edit()
                .remove(LAST_STATE_KEY)
                .apply()
        }
    }
    fun restoreState(): State{
        val buffer = pref.getString(BUFFER_STATE_KEY, null)
        val bufferClearRequest = pref.getBoolean(BUFFER_CLEAR_REQUEST_KEY, false)
        val memory = pref.getString(MEMORY_STATE_KEY, null)
        val binary = storeStringToOperation(pref.getString(BINARY_STATE_KEY, null))
        val last = storeStringToOperation(pref.getString(LAST_STATE_KEY, null))
        return State(0, buffer.toValue(), bufferClearRequest, memory.toValue(), binary, last)
    }

    //CONVERTERS
    fun operationToStoreString(value: Operation?): String? =
        when(value){
            is UnaryOperation -> {
                "${value.id};${value.a}"
            }
            is BinaryOperation -> {
                "${value.id};${value.a};${value.b}"
            }
            else-> null
        }

    fun storeStringToOperation(value: String?): Operation?{
        value?.let {
            val list = it.split(';')
            var a: Value? = null
            if (list.size > 1 && list[1] != "null") a = list[1].toValue()
            var b: Value? = null
            if (list.size > 2 && list[2] != "null") b = list[2].toValue()
            return when(list[0]){
                Operations.ADD_ID -> {
                    Operations.Add().apply {
                        this.a = a
                        this.b = b
                    }
                }
                Operations.SUBTRACT_ID -> {
                    Operations.Subtract().apply {
                        this.a = a
                        this.b = b
                    }
                }
                Operations.MULTIPLY_ID -> {
                    Operations.Multiply().apply {
                        this.a = a
                        this.b = b
                    }
                }
                Operations.DIVIDE_ID ->{
                    Operations.Divide().apply {
                        this.a = a
                        this.b = b
                    }
                }
                /*Operations.SQR_ID ->{
                    Operations.Sqr().apply {
                        this.a = a
                    }
                }
                Operations.SQRT_ID ->{
                    Operations.Sqrt().apply {
                        this.a = a
                    }
                }
                Operations.SIN_ID ->{
                    Operations.Sin().apply {
                        this.a = a
                    }
                }
                Operations.COS_ID ->{
                    Operations.Cos().apply {
                        this.a = a
                    }
                }
                Operations.TAN_ID ->{
                    Operations.Tan().apply {
                        this.a = a
                    }
                }*/
                else-> null
            }
        }
        return null
    }
    //endregion
}