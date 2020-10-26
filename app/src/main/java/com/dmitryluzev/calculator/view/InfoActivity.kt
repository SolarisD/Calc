package com.dmitryluzev.calculator.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dmitryluzev.calculator.BuildConfig
import com.dmitryluzev.calculator.R
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tv_build_type.text = BuildConfig.BUILD_TYPE
        tv_app_version.text = BuildConfig.VERSION_NAME
    }
}