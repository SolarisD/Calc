package com.dmitryluzev.calculator.view.main

import android.content.ClipData
import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN
import android.content.ClipboardManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dmitryluzev.calculator.R
import com.dmitryluzev.calculator.adapter.OperationViewAdapter
import com.dmitryluzev.calculator.app.Pref
import com.dmitryluzev.calculator.core.Calculator
import com.dmitryluzev.calculator.databinding.ActivityMainBinding
import com.dmitryluzev.calculator.model.Repo
import com.dmitryluzev.calculator.view.history.HistoryActivity
import com.dmitryluzev.calculator.view.InfoActivity
import com.dmitryluzev.calculator.view.keyboard.KeyboardFragment
import com.dmitryluzev.calculator.view.SettingsActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object{
        const val DISPLAY_INCLUDE_COPY = 101;
        const val DISPLAY_INCLUDE_PASTE = 102;
    }

    private lateinit var vm: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        val calc = Calculator.getInstance()
        val repo = Repo.getInstance(application)
        val pref = Pref.getInstance(application)
        vm = ViewModelProvider(this, MainViewModelFactory(calc, repo, pref))
            .get(MainViewModel::class.java)
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.vm = vm
        binding.rvOperations.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
        vm.operationDisplay.observe(this){
            binding.rvOperations.adapter = OperationViewAdapter(it)
        }
        supportActionBar?.elevation = 0f
        registerForContextMenu(display_view)
        loadKeyboardFragment()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.settings_menu_item-> startActivity(Intent(this, SettingsActivity::class.java))
            R.id.about_menu_item-> startActivity(Intent(this, InfoActivity::class.java))
        }
        return true
    }
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        when(v){
            display_view->{
                /*menu?.setHeaderTitle("Copy/paste menu")*/
                menu?.add(Menu.NONE, DISPLAY_INCLUDE_COPY, Menu.NONE, "Copy");
                menu?.add(Menu.NONE, DISPLAY_INCLUDE_PASTE, Menu.NONE, "Paste")
            }
        }
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            DISPLAY_INCLUDE_COPY ->{
                tv_buffer.text?.let {
                    val cbm = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                    cbm.setPrimaryClip(ClipData.newPlainText(getString(R.string.app_label), it))
                    //Toast.makeText(this, resources.getString(R.string.value_copied, it), Toast.LENGTH_SHORT).show()
                }
            }
            DISPLAY_INCLUDE_PASTE ->{
                val clip = (getSystemService(CLIPBOARD_SERVICE) as ClipboardManager).primaryClip
                clip?.let {
                    if(it.description.hasMimeType(MIMETYPE_TEXT_PLAIN)){
                        it.getItemAt(0).text?.let {text->
                            val result = vm.pasteFromClipboard(text.toString())
                            if (result != null) Toast.makeText(this, resources.getString(R.string.value_pasted, text), Toast.LENGTH_SHORT).show()
                            else Toast.makeText(this, resources.getString(R.string.value_not_pasted, text), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
        return super.onContextItemSelected(item)
    }
    override fun onResume() {
        super.onResume()
        loadKeyboardFragment()
    }
    override fun onSaveInstanceState(outState: Bundle) {
        vm.saveState()
        super.onSaveInstanceState(outState)
    }
    private fun loadKeyboardFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.keyboard_layout, KeyboardFragment())
            .commit()
    }

    fun startHistoryActivity(view: View) {startActivity(Intent(this, HistoryActivity::class.java))}
}