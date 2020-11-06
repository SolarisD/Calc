package com.dmitryluzev.calculator.view

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.dmitryluzev.calculator.R


class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.calc_settings, rootKey)
    }
}
