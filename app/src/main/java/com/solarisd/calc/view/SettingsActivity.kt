package com.solarisd.calc.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.solarisd.calc.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    /*private fun loadDefKeyboard() {
        vm.kMode = "DEF"
        supportFragmentManager.commit {
            replace<DefKeyboardFragment>(R.id.keyboard_layout, null, intent.extras)
        }
    }
    private fun loadExtKeyboard() {
        vm.kMode = "EXT"
        supportFragmentManager.commit {
            replace<ExtKeyboardFragment>(R.id.keyboard_layout, null, intent.extras)
        }
    }*/
}