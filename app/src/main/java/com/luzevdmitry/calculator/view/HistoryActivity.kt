package com.luzevdmitry.calculator.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.luzevdmitry.calculator.R
import com.luzevdmitry.calculator.adapter.HistoryViewAdapter
import com.luzevdmitry.calculator.viewmodel.HistoryViewModel
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    private val vm: HistoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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