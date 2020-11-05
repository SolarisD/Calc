package com.dmitryluzev.calculator.view.main

import android.content.ClipData
import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN
import android.content.ClipboardManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dmitryluzev.calculator.R
import com.dmitryluzev.calculator.app.Pref
import com.dmitryluzev.calculator.core.Calculator
import com.dmitryluzev.calculator.databinding.ActivityMainBinding
import com.dmitryluzev.calculator.model.DB
import com.dmitryluzev.calculator.model.Repo
import com.dmitryluzev.calculator.view.history.HistoryActivity
import com.dmitryluzev.calculator.view.InfoActivity
import com.dmitryluzev.calculator.view.calculator.CalculatorFragment
import com.dmitryluzev.calculator.view.SettingsActivity

class MainActivity : AppCompatActivity() {
    private lateinit var vm: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        val calc = Calculator.getInstance()
        val dao = DB.getInstance(applicationContext).dao
        val repo = Repo(dao)
        val pref = Pref.getInstance(application)
        vm = ViewModelProvider(this, MainViewModelFactory(calc, repo, pref))
            .get(MainViewModel::class.java)
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        binding.mainToolbar
        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        setContentView(binding.root)
        loadKeyboardFragment()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.history_menu_item-> startActivity(Intent(this, HistoryActivity::class.java))
            R.id.settings_menu_item-> startActivity(Intent(this, SettingsActivity::class.java))
            R.id.about_menu_item-> startActivity(Intent(this, InfoActivity::class.java))
        }
        return true
    }


    override fun onResume() {
        super.onResume()
        loadKeyboardFragment()
    }
    override fun onSaveInstanceState(outState: Bundle) {
        vm.saveState()
        super.onSaveInstanceState(outState)
    }
    private fun loadKeyboardFragment() {
        /*supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragments_layout, CalculatorFragment())
            .commit()*/
    }
}