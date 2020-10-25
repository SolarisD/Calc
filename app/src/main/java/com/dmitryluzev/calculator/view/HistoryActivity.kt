package com.dmitryluzev.calculator.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.dmitryluzev.calculator.R
import com.dmitryluzev.calculator.adapter.HistoryViewAdapter
import com.dmitryluzev.calculator.app.App
import com.dmitryluzev.calculator.viewmodel.HistoryViewModel
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class HistoryActivity : AppCompatActivity() {
    @Inject lateinit var vm: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.historyComponent().create().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0f

        rcv_history.layoutManager = LinearLayoutManager(this)
        vm.historyRecords.observe(this){
            it?.let {
                rcv_history.adapter = HistoryViewAdapter(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.history_delete_item-> {
                GlobalScope.launch(Dispatchers.IO){
                    vm.clearHistory()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}