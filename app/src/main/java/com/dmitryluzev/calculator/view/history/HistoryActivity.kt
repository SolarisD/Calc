package com.dmitryluzev.calculator.view.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dmitryluzev.calculator.R
import com.dmitryluzev.calculator.adapter.HistoryViewAdapter
import com.dmitryluzev.calculator.databinding.ActivityHistoryBinding
import com.dmitryluzev.calculator.model.DB
import com.dmitryluzev.calculator.model.Repo
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    private lateinit var vm: HistoryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        val dao = DB.getInstance(applicationContext).dao
        val repo = Repo(dao)
        vm = ViewModelProvider(this, HistoryViewModelFactory(repo))
            .get(HistoryViewModel::class.java)
        super.onCreate(savedInstanceState)
        val binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.vm = vm
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