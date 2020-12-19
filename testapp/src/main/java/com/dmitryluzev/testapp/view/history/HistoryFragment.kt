package com.dmitryluzev.testapp.view.history

import android.content.*
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dmitryluzev.testapp.R
import com.dmitryluzev.testapp.databinding.FragmentHistoryBinding
import com.dmitryluzev.testapp.model.DB
import com.dmitryluzev.testapp.model.Repo

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
        binding.rcvHistory.adapter = HistoryAdapter(::showCSPopup)
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

    private fun showCSPopup(textView: TextView): Boolean {
        textView.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
        textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.onSc))
        textView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.sc))
        val popup = PopupMenu(this.context, textView)
        popup.inflate(R.menu.cs_menu)
        popup.setOnDismissListener {
            textView.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.onBackgroundColor
                )
            )
            textView.background = null
        }
        popup.setOnMenuItemClickListener {
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
                        this.context,
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
        popup.show()
        return false
    }
}