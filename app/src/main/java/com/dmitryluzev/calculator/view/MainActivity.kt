package com.dmitryluzev.calculator.view

import android.os.Bundle
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.dmitryluzev.calculator.R
import com.dmitryluzev.calculator.app.PrefManager
import com.dmitryluzev.calculator.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var prefManager: PrefManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.mainToolbar
        setSupportActionBar(binding.mainToolbar)
        navController = findNavController(R.id.navHostFragment)
        drawerLayout = binding.drawerLayout
        prefManager = PrefManager.getInstance(application)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        //NavigationUI.setupWithNavController(binding.navigationView, navController)

        ////SETUP navigation menu
        val darkCheckBox = binding.navigationView.menu.findItem(R.id.themeDarkEnable).actionView as CheckBox
        val hapticCheckBox = binding.navigationView.menu.findItem(R.id.hapticButtonsEnable).actionView as CheckBox
        val soundCheckBox = binding.navigationView.menu.findItem(R.id.soundButtonsEnable).actionView as CheckBox
        prefManager.livePref.observe(this){
            it?.let {
                if (darkCheckBox.isChecked != it.dark) darkCheckBox.isChecked = it.dark
                if (hapticCheckBox.isChecked != it.haptic) hapticCheckBox.isChecked = it.haptic
                if (soundCheckBox.isChecked != it.sound) soundCheckBox.isChecked = it.sound
            }
        }
        darkCheckBox.setOnCheckedChangeListener { _, isChecked ->
            prefManager.saveDark(isChecked)
        }
        hapticCheckBox.setOnCheckedChangeListener { _, isChecked ->
            prefManager.saveHaptic(isChecked)
        }
        soundCheckBox.setOnCheckedChangeListener { _, isChecked ->
            prefManager.saveSound(isChecked)
        }

        binding.navigationView.setNavigationItemSelectedListener { menuItem->
            when(menuItem.itemId){
                R.id.historyFragment -> {
                    navController.navigate(R.id.action_global_historyFragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    return@setNavigationItemSelectedListener true
                }/*
                R.id.settingsFragment -> {
                    navController.navigate(R.id.action_global_settingsFragment)
                }
                R.id.infoFragment -> {
                    navController.navigate(R.id.action_global_infoFragment)
                }*/
            }
            false
        }

        binding.btnInfo.setOnClickListener {
            navController.navigate(R.id.action_global_infoFragment)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}