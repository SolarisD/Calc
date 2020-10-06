package com.solarisd.calc.view


import android.content.ClipData
import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN
import android.content.ClipboardManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.SavedStateViewModelFactory
import com.solarisd.calc.R
import com.solarisd.calc.app.AppManager
import com.solarisd.calc.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.display.view.*

class MainActivity : AppCompatActivity() {
    companion object{
        const val DISPLAY_INCLUDE_COPY = 101;
        const val DISPLAY_INCLUDE_PASTE = 102;
    }
    private val vm: MainViewModel by viewModels{SavedStateViewModelFactory(application, this)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registration()
        loadKeyboardFragment()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.settings_menu_item-> showSettingsActivity()
            /*R.id.about_menu_item-> showAboutActivity()*/
        }
        return true
    }
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        when(v){
            display_include->{
                /*menu?.setHeaderTitle("Copy/paste menu")*/
                menu?.add(Menu.NONE, DISPLAY_INCLUDE_COPY, Menu.NONE, "Copy");
                menu?.add(Menu.NONE, DISPLAY_INCLUDE_PASTE, Menu.NONE, "Paste")
            }
        }
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            DISPLAY_INCLUDE_COPY->{
                display_include.tv_buffer.text?.let {
                    val cbm = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                    cbm.setPrimaryClip(ClipData.newPlainText(getString(R.string.app_label), it))
                    Toast.makeText(this, resources.getString(R.string.value_copied, it), Toast.LENGTH_SHORT).show()
                }
            }
            DISPLAY_INCLUDE_PASTE->{
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
    private fun registration(){
        vm.bufferDisplay.observe(this, { display_include.tv_buffer.text = it})
        vm.memoryDisplay.observe(this, { display_include.tv_memory.text = it})
        vm.operationDisplay.observe(this, { display_include.tv_operation.text = it})
        display_include.setOnClickListener { showHistoryActivity() }
        registerForContextMenu(display_include)
    }
    private fun loadKeyboardFragment() {
        if(AppManager.keyboard){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.keyboard_layout, ExtKeyboardFragment())
                .commit()
        }else{
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.keyboard_layout, DefKeyboardFragment())
                .commit()
        }
    }
    private fun showSettingsActivity() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }
    private fun showAboutActivity() {
        startActivity(Intent(this, AboutActivity::class.java))
    }
    private fun showHistoryActivity() {
        startActivity(Intent(this, HistoryActivity::class.java))
    }
}