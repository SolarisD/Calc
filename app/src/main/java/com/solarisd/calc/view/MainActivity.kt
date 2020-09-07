package com.solarisd.calc.view


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.solarisd.calc.R
import com.solarisd.calc.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val vm: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerDisplays()
        loadKeyboard()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.settings_menu_item-> showSettingsActivity()
            R.id.about_menu_item-> showAboutActivity()
        }
        return true
    }
    private fun registerDisplays(){
        vm.mainDisplay.observe(this, { tv_main.text = it})
        vm.memoryDisplay.observe(this, { tv_memory.text = it})
        vm.historyDisplay.observe(this, { tv_history.text = it})
        tv_history.setOnClickListener { loadHistory() }
    }
    private fun loadKeyboard() {
        when(vm.kMode){
            "DEF"-> supportFragmentManager
                .beginTransaction()
                .replace(R.id.keyboard_layout, DefKeyboardFragment())
                .commit()
            "EXT"-> supportFragmentManager
                .beginTransaction()
                .replace(R.id.keyboard_layout, ExtKeyboardFragment())
                .commit()
        }
    }
    private fun loadHistory() {
        supportFragmentManager.commit {
            replace<HistoryFragment>(R.id.keyboard_layout, null, intent.extras)
            addToBackStack(null)
        }
    }
    private fun showSettingsActivity() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }
    private fun showAboutActivity() {
        startActivity(Intent(this, AboutActivity::class.java))
    }
}