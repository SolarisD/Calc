package com.dmitryluzev.calculator.view.calculator

import android.content.*
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.dmitryluzev.calculator.R
import com.dmitryluzev.calculator.app.Pref
import com.dmitryluzev.calculator.app.Sound
import com.dmitryluzev.calculator.core.Calculator
import com.dmitryluzev.calculator.databinding.FragmentCalculatorBinding
import com.dmitryluzev.calculator.model.DB
import com.dmitryluzev.calculator.model.Repo

class CalculatorFragment : Fragment() {
    private lateinit var vm: CalculatorViewModel
    private lateinit var binding: FragmentCalculatorBinding
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val application = requireNotNull(this.activity).application
        val dao = DB.getInstance(application).dao
        val repo = Repo(dao)
        val pref = Pref.getInstance(application)
        val sound = Sound.getInstance(application)
        val calculator = Calculator.getInstance()
        vm = ViewModelProvider(this, KeyboardViewModelFactory(calculator, repo, pref, sound)).get(CalculatorViewModel::class.java)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCalculatorBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.vm = vm
        registerForContextMenu(binding.tvBuffer)
        /*setHasOptionsMenu(true)*/
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
                    //Toast.makeText(this, resources.getString(R.string.value_copied, it), Toast.LENGTH_SHORT).show()
                }
            }
            R.id.paste_menu_item->{
                val clip = (requireActivity().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager).primaryClip
                clip?.let {
                    if(it.description.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)){
                        it.getItemAt(0).text?.let {text->
                            val result = vm.pasteFromClipboard(text.toString())
                            /*if (result != null) Toast.makeText(this, resources.getString(R.string.value_pasted, text), Toast.LENGTH_SHORT).show()
                            else Toast.makeText(this, resources.getString(R.string.value_not_pasted, text), Toast.LENGTH_SHORT).show()*/
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

    /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.history_menu_item -> findNavController().navigate(CalculatorFragmentDirections.actionCalculatorFragmentToHistoryFragment())
            R.id.settings_menu_item -> findNavController().navigate(CalculatorFragmentDirections.actionGlobalSettingsFragment())
            R.id.about_menu_item -> findNavController().navigate(CalculatorFragmentDirections.actionGlobalInfoFragment())
        }
        return super.onOptionsItemSelected(item)
    }*/
}



