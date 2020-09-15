package com.solarisd.calc.view

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.preference.CheckBoxPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.solarisd.calc.R
import com.solarisd.calc.viewmodel.MainViewModel

class SettingsActivity : AppCompatActivity() {
    private val vm: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(vm.darkTheme) setTheme(R.style.AppThemeDark)
        setContentView(R.layout.activity_settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings_layout, SettingsFragment())
            .commit()
        vm.pref.registerOnSharedPreferenceChangeListener(::listener)

    }

    class SettingsFragment: PreferenceFragmentCompat(){
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.calc_settings, rootKey)
        }
    }

    override fun onDestroy() {
        vm.pref.unregisterOnSharedPreferenceChangeListener(::listener)
        super.onDestroy()
    }

    fun listener(sharedPreferences: SharedPreferences, s: String){
        Log.d("PREF CHANGE", s)
        if (s == "dark_theme") recreate()
    }
}
