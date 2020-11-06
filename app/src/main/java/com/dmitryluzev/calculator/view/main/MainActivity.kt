package com.dmitryluzev.calculator.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.dmitryluzev.calculator.R
import com.dmitryluzev.calculator.app.Pref
import com.dmitryluzev.calculator.core.Calculator
import com.dmitryluzev.calculator.databinding.ActivityMainBinding
import com.dmitryluzev.calculator.model.DB
import com.dmitryluzev.calculator.model.Repo
import com.dmitryluzev.calculator.view.history.HistoryFragment
import com.dmitryluzev.calculator.view.InfoActivity
import com.dmitryluzev.calculator.view.SettingsActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        binding.mainToolbar
        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        setContentView(binding.root)
        val navController = findNavController(R.id.navHostFragment)
        NavigationUI.setupWithNavController(binding.mainToolbar, navController)
    }


    /* override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.history_menu_item-> {}
            R.id.settings_menu_item-> startActivity(Intent(this, SettingsActivity::class.java))
            R.id.about_menu_item-> startActivity(Intent(this, InfoActivity::class.java))
        }
        return true
    }*/
}