package com.solarisd.calc.view

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import com.solarisd.calc.R
import com.solarisd.calc.viewmodel.MainViewModel

class SettingsActivity : AppCompatActivity() {
    private val vm: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings_layout, SettingsFragment())
            .commit()
        vm.pref.registerOnSharedPreferenceChangeListener(::prefChangeListener)
    }

    class SettingsFragment: PreferenceFragmentCompat(){
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.calc_settings, rootKey)
        }
    }

    override fun onDestroy() {
        vm.pref.unregisterOnSharedPreferenceChangeListener(::prefChangeListener)
        super.onDestroy()
    }
    private fun prefChangeListener(sharedPreferences: SharedPreferences, s: String){
        if (s == "dark_theme"){
            if (vm.darkTheme) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}
