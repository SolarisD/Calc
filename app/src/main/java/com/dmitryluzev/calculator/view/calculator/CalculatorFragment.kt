package com.dmitryluzev.calculator.view.calculator

import android.content.*
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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
        val calculator = Calculator(pref.restoreState())
        vm = ViewModelProvider(this, CalculatorViewModelFactory(calculator, repo, pref, sound)).get(CalculatorViewModel::class.java)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCalculatorBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.vm = vm
        val manager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        binding.rcvHistory.layoutManager = manager
        binding.rcvHistory.adapter = CalculatorAdapter(::showCSPopup)
        binding.rcvHistory.itemAnimator = null
        binding.tvBuffer.setOnLongClickListener {
            showPCSPopup(it as TextView)
        }
        binding.tvMemory.setOnLongClickListener {
            showCSPopup(it as TextView)
        }
        return binding.root
    }
    override fun onSaveInstanceState(outState: Bundle) {
        vm.saveState()
        super.onSaveInstanceState(outState)
    }
    private fun showCSPopup(textView: TextView): Boolean {
        textView.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
        textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.onSc))
        textView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.sc))
        PopupMenu(this.context, textView).apply {
            inflate(R.menu.cs_menu)
            setOnDismissListener {
                textView.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.onBackgroundColor
                    )
                )
                textView.background = null
            }
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.copy_menu_item -> {
                        val cbm =
                            requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        cbm.setPrimaryClip(
                            ClipData.newPlainText(
                                getString(R.string.app_label),
                                textView.text
                            )
                        )
                        Toast.makeText(
                            requireContext(),
                            resources.getString(R.string.value_copied, textView.text),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    R.id.share_menu_item -> {
                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, textView.text)
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        startActivity(shareIntent)
                    }
                }
                return@setOnMenuItemClickListener false
            }
            show()
        }
        return false
    }
    private fun showPCSPopup(textView: TextView): Boolean {
        textView.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
        textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.onSc))
        textView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.sc))
        PopupMenu(this.context, textView).apply {
            inflate(R.menu.pcs_menu)
            setOnDismissListener {
                textView.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.onBackgroundColor
                    )
                )
                textView.background = null
            }
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.paste_menu_item -> {
                        val clip = (requireActivity().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager).primaryClip
                        clip?.let {
                            if(it.description.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)){
                                it.getItemAt(0).text?.let {text->
                                    vm.pasteFromClipboard(text.toString())
                                }
                            }
                        }
                    }
                    R.id.copy_menu_item -> {
                        val cbm =
                            requireActivity().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                        cbm.setPrimaryClip(
                            ClipData.newPlainText(
                                getString(R.string.app_label),
                                textView.text
                            )
                        )
                        Toast.makeText(
                            requireContext(),
                            resources.getString(R.string.value_copied, textView.text),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    R.id.share_menu_item -> {
                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, textView.text)
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        startActivity(shareIntent)
                    }
                }
                return@setOnMenuItemClickListener false
            }
            show()
        }
        return false
    }
}



