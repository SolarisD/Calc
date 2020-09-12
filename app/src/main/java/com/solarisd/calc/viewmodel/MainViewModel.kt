package com.solarisd.calc.viewmodel

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.lifecycle.*
import androidx.preference.PreferenceManager
import com.solarisd.calc.core.Calculator
import com.solarisd.calc.core.Operations
import com.solarisd.calc.core.enums.Buttons
import com.solarisd.calc.core.enums.Symbols
import com.solarisd.calc.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application){
    //PRIVATE
    private val c = Calculator()
    private val v = application.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    private val dao: Dao = DB.getInstance(application).dao()
    private val pref by lazy { PreferenceManager.getDefaultSharedPreferences(application) }
    private val vibro: Boolean
    get() = pref.getBoolean("vibration_buttons", false)
    private fun vibrate(){
        if (vibro){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                v.vibrate(50);
            }
        }
    }
    //PUBLIC
    val keyboard: Boolean
    get() = pref.getBoolean("extended_keyboard", false)
    val mainDisplay:  LiveData<String> = Transformations.map(c.buffer){
        it?.toString() ?: "0"
    }
    val memoryDisplay:  LiveData<String> = Transformations.map(c.memory){
        if (it.isNullOrEmpty()) ""
        else "M: $it"
    }
    val historyDisplay:  LiveData<String> = Transformations.map(c.history){
        //SAVE DATA TO DB
        it?.let {
            if (it.result!=null){
                viewModelScope.launch(Dispatchers.IO) {
                    dao.insert(Record(op = it.op.symbol, a = it.a, b = it.b, result = it.result))
                }
            }
        }
        //POST TO DISPLAY
        it?.toString() ?: ""
    }
    val historyRecords: LiveData<List<Record>> = dao.getLiveRecords()
    fun buttonPressed(button: Buttons){
        vibrate()
        when(button) {
            Buttons.ZERO-> c.symbol(Symbols.ZERO)
            Buttons.ONE-> c.symbol(Symbols.ONE)
            Buttons.TWO-> c.symbol(Symbols.TWO)
            Buttons.THREE-> c.symbol(Symbols.THREE)
            Buttons.FOUR-> c.symbol(Symbols.FOUR)
            Buttons.FIVE-> c.symbol(Symbols.FIVE)
            Buttons.SIX-> c.symbol(Symbols.SIX)
            Buttons.SEVEN-> c.symbol(Symbols.SEVEN)
            Buttons.EIGHT->  c.symbol(Symbols.EIGHT)
            Buttons.NINE-> c.symbol(Symbols.NINE)
            Buttons.DOT-> c.symbol(Symbols.DOT)
            Buttons.NEGATIVE -> c.negative()
            Buttons.CLEAR-> c.clear()
            Buttons.BACKSPACE-> c.backspace()
            Buttons.RESULT-> c.result()
            Buttons.PLUS-> c.operation(Operations.Add())
            Buttons.MINUS-> c.operation(Operations.Subtract())
            Buttons.MULTIPLY-> c.operation(Operations.Multiply())
            Buttons.DIVIDE-> c.operation(Operations.Divide())
            Buttons.PERCENT-> c.percent()
            Buttons.SQR-> c.operation(Operations.Sqr())
            Buttons.SQRT-> c.operation(Operations.Sqrt())
            Buttons.SIN-> c.operation(Operations.Sin())
            Buttons.COS-> c.operation(Operations.Cos())
            Buttons.TAN-> c.operation(Operations.Tan())
            Buttons.M_CLEAR-> c.memoryClear()
            Buttons.M_PLUS-> c.memoryPlus()
            Buttons.M_MINUS-> c.memoryMinus()
            Buttons.M_RESTORE-> c.memoryRestore()
        }
    }
    fun clearHistory() {
        dao.deleteAll()
        c.historyClear()
    }

}