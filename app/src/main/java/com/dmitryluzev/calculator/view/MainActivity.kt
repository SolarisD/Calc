package com.dmitryluzev.calculator.view

import android.content.Intent
import androidx.appcompat.widget.ShareActionProvider
import android.os.Bundle
import android.view.Menu
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.MenuItemCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.dmitryluzev.calculator.R
import com.dmitryluzev.calculator.app.Pref
import com.dmitryluzev.calculator.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var pref: Pref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.mainToolbar
        setSupportActionBar(binding.mainToolbar)
        navController = findNavController(R.id.navHostFragment)
        drawerLayout = binding.drawerLayout
        pref = Pref.getInstance(application)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        //NavigationUI.setupWithNavController(binding.navigationView, navController)

        ////SETUP navigation menu
        val darkThemeCheckBox = binding.navigationView.menu.findItem(R.id.themeDarkEnable).actionView as CheckBox
        darkThemeCheckBox.setOnCheckedChangeListener { _, isChecked ->
            pref.saveDark(isChecked)
        }
        pref.liveDark.observe(this){
            if (darkThemeCheckBox.isChecked != it) darkThemeCheckBox.isChecked = it
        }

        binding.navigationView.setNavigationItemSelectedListener { menuItem->
            when(menuItem.itemId){
                R.id.historyFragment -> {
                    navController.navigate(R.id.action_global_historyFragment)
                }
                R.id.settingsFragment -> {
                    navController.navigate(R.id.action_global_settingsFragment)
                }
                R.id.infoFragment -> {
                    navController.navigate(R.id.action_global_infoFragment)
                }
            }
            menuItem.isChecked = true
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}