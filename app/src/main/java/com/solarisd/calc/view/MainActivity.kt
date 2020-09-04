package com.solarisd.calc.view


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
            R.id.default_item-> setDefKeyboard()
            R.id.extented_item-> setExtKeyboard()
            R.id.vibro_on_item->{vm.vMode = true}
            R.id.vibro_off_item->{vm.vMode = false}
        }
        return true
    }
    private fun registerDisplays(){
        vm.mainDisplay.observe(this, { tv_main.text = it})
        vm.memoryDisplay.observe(this, { tv_memory.text = it})
        vm.historyDisplay.observe(this, { tv_history.text = it})
    }
    private fun loadKeyboard(){
        when(vm.kMode){
            "DEF"-> supportFragmentManager.commit {
                add<DefKeyboardFragment>(R.id.keyboard_layout, null, intent.extras)
            }
            "EXT"-> supportFragmentManager.commit {
                add<ExtKeyboardFragment>(R.id.keyboard_layout, null, intent.extras)
            }
        }
    }

    private fun setDefKeyboard() {
        vm.kMode = "DEF"
        supportFragmentManager.commit {
            replace<DefKeyboardFragment>(R.id.keyboard_layout, null, intent.extras)
        }
    }
    private fun setExtKeyboard() {
        vm.kMode = "EXT"
        supportFragmentManager.commit {
            replace<ExtKeyboardFragment>(R.id.keyboard_layout, null, intent.extras)
        }
    }
}