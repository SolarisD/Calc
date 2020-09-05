package com.solarisd.calc.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.solarisd.calc.R
import com.solarisd.calc.adapter.HistoryViewAdapter
import com.solarisd.calc.viewmodel.MainViewModel

class HistoryFragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = HistoryFragment()
    }
    private lateinit var recyclerView: RecyclerView
    private val vm: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_history, container, false)
        recyclerView = root.findViewById(R.id.rcv_history)
        recyclerView.layoutManager = LinearLayoutManager(this.activity)
        recyclerView.adapter = HistoryViewAdapter(vm.getHistoryRecords())
        return root
    }
}