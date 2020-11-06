package com.dmitryluzev.calculator.view.history

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dmitryluzev.calculator.R
import com.dmitryluzev.calculator.adapter.HistoryViewAdapter
import com.dmitryluzev.calculator.databinding.FragmentHistoryBinding
import com.dmitryluzev.calculator.model.DB
import com.dmitryluzev.calculator.model.Repo

class HistoryFragment : Fragment() {

    private lateinit var vm: HistoryViewModel
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val dao = DB.getInstance(context).dao
        val repo = Repo(dao)
        vm = ViewModelProvider(this, HistoryViewModelFactory(repo)).get(HistoryViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentHistoryBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.vm = vm
        binding.rcvHistory.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        vm.historyRecords.observe(viewLifecycleOwner){
            it?.let {
                binding.rcvHistory.adapter = HistoryViewAdapter(it)
            }
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.history_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.history_delete_item-> {
                vm.clearHistory()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    /*override fun onCreate(savedInstanceState: Bundle?) {
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
    }*/
}