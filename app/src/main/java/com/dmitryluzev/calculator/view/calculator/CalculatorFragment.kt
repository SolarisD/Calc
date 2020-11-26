package com.dmitryluzev.calculator.view.calculator

import android.content.ClipData
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dmitryluzev.calculator.R
import com.dmitryluzev.calculator.app.PrefManager
import com.dmitryluzev.calculator.app.Sound
import com.dmitryluzev.calculator.databinding.FragmentCalculatorBinding
import com.dmitryluzev.calculator.model.DB
import com.dmitryluzev.calculator.model.Repo
import com.dmitryluzev.core.Calculator

class CalculatorFragment : Fragment() {
    private lateinit var vm: CalculatorViewModel
    private lateinit var binding: FragmentCalculatorBinding
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val application = requireNotNull(this.activity).application
        val dao = DB.getInstance(application).dao
        val repo = Repo(dao)
        val pref = PrefManager.getInstance(application)
        val sound = Sound.getInstance(application)
        val calculator = Calculator.getInstance()
        vm = ViewModelProvider(this, CalculatorViewModelFactory(calculator, repo, pref, sound)).get(CalculatorViewModel::class.java)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCalculatorBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.vm = vm
        registerForContextMenu(binding.tvBuffer)
        val manager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, true)
        binding.rcvHistory.layoutManager = manager
        val adapter = HistoryPreviewAdapter()
        binding.rcvHistory.adapter = adapter
        vm.historyDisplay.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        return binding.root
    }
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        requireActivity().menuInflater.inflate(R.menu.copy_paste_menu, menu)
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.copy_menu_item->{
                binding.tvBuffer.text?.let {
                    val cbm = requireActivity().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                    cbm.setPrimaryClip(ClipData.newPlainText(getString(R.string.app_label), it))
                    Toast.makeText(this.context, resources.getString(R.string.value_copied, it), Toast.LENGTH_SHORT).show()
                }
            }
            R.id.paste_menu_item->{
                val clip = (requireActivity().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager).primaryClip
                clip?.let {
                    if(it.description.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)){
                        it.getItemAt(0).text?.let {text->
                            vm.pasteFromClipboard(text.toString())
                        }
                    }
                }
            }
        }
        return super.onContextItemSelected(item)
    }
    override fun onSaveInstanceState(outState: Bundle) {
        vm.saveState()
        super.onSaveInstanceState(outState)
    }
}



