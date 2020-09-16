package com.solarisd.calc.view


import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.solarisd.calc.R
import com.solarisd.calc.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val vm: MainViewModel by viewModels()
    private var recreateCall = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(vm.darkTheme) setTheme(R.style.AppThemeDark)
        setContentView(R.layout.activity_main)
        registerListeners()
        loadKeyboard()
    }
    override fun onResume() {
        super.onResume()
        if(recreateCall){
            recreateCall = false
            recreate()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.settings_menu_item-> {showSettingsActivity(); recreateCall = true}
            R.id.about_menu_item-> showAboutActivity()
        }
        return true
    }

    private fun registerListeners(){
        vm.bufferDisplay.observe(this, { tv_main.text = it})
        vm.memoryDisplay.observe(this, { tv_memory.text = it})
        vm.historyDisplay.observe(this, { tv_history.text = it})
        tv_history.setOnClickListener { showHistoryActivity() }
    }
    private fun loadKeyboard() {
        if(vm.keyboard){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.keyboard_layout, ExtKeyboardFragment())
                .commit()
        }else{
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.keyboard_layout, DefKeyboardFragment())
                .commit()
        }
    }
    private fun showSettingsActivity() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }
    private fun showAboutActivity() {
        startActivity(Intent(this, AboutActivity::class.java))
    }
    private fun showHistoryActivity() {
        startActivity(Intent(this, HistoryActivity::class.java))
    }
}