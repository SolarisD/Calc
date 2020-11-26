package com.dmitryluzev.calculator.view.history

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dmitryluzev.calculator.R
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
        binding.rcvHistory.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, true)
        val adapter = HistoryViewAdapter(){
            it?.let {
                val cbm = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                cbm.setPrimaryClip(ClipData.newPlainText(getString(R.string.app_label), it.toString()))
                Toast.makeText(this.context, resources.getString(R.string.value_copied, it), Toast.LENGTH_SHORT).show()
            }
        }
        binding.rcvHistory.adapter = adapter
        vm.historyRecords.observe(viewLifecycleOwner){
            adapter.submitRecordList(it)
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
}