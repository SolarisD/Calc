package com.solarisd.calc.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.solarisd.calc.R
import com.solarisd.calc.adapter.HistoryViewAdapter
import com.solarisd.calc.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    private val vm: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(vm.darkTheme) setTheme(R.style.AppThemeDark)
        setContentView(R.layout.activity_history)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        btn_history_close.setOnClickListener {
            finish()
        }
        btn_history_clear.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO){
                vm.clearHistory()
            }
        }
        rcv_history.layoutManager = LinearLayoutManager(this)
        vm.historyRecords.observe(this){
                it?.let {
                rcv_history.adapter = HistoryViewAdapter(it)
            }
        }
    }
}