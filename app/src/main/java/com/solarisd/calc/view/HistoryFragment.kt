package com.solarisd.calc.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.solarisd.calc.R
import com.solarisd.calc.adapter.HistoryViewAdapter
import com.solarisd.calc.viewmodel.MainViewModel
import kotlinx.coroutines.*

class HistoryFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private val vm: MainViewModel by activityViewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_history, container, false)
        root.findViewById<Button>(R.id.btn_history_clear).setOnClickListener {
            GlobalScope.launch(Dispatchers.IO){
                vm.clearHistory()
            }
        }
        root.findViewById<Button>(R.id.btn_history_close).setOnClickListener{
            parentFragmentManager.popBackStack()
        }
        recyclerView = root.findViewById(R.id.rcv_history)
        recyclerView.layoutManager = LinearLayoutManager(this.activity)
        vm.historyRecords.observe(this.viewLifecycleOwner){
            it?.let {
                recyclerView.adapter = HistoryViewAdapter(it)
            }
        }
        return root
    }
}