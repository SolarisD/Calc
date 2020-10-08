package com.dmitryluzev.calculator.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dmitryluzev.calculator.R

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}