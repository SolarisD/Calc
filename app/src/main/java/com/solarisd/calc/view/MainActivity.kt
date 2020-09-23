package com.solarisd.calc.view


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.SavedStateViewModelFactory
import com.solarisd.calc.R
import com.solarisd.calc.app.AppManager
import com.solarisd.calc.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val vm: MainViewModel by viewModels{SavedStateViewModelFactory(application, this)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerListeners()
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
            /*R.id.about_menu_item-> showAboutActivity()*/
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        loadKeyboard()
    }
    private fun registerListeners(){
        vm.bufferDisplay.observe(this, { tv_buffer.text = it})
        vm.memoryDisplay.observe(this, { tv_memory.text = it})
        vm.operationDisplay.observe(this, { tv_operation.text = it})
        tv_operation.setOnClickListener { showHistoryActivity() }
    }
    private fun loadKeyboard() {
        if(AppManager.keyboard){
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