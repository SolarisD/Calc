package com.solarisd.calc.viewmodel

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.lifecycle.*
import androidx.preference.PreferenceManager
import com.solarisd.calc.core.Calculator
import com.solarisd.calc.core.enums.Buttons
import com.solarisd.calc.core.enums.Operators
import com.solarisd.calc.core.enums.Symbols
import com.solarisd.calc.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application){
    //PRIVATE
    private val fsm = Calculator()
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
    get()  = pref.getBoolean("extended_keyboard", false)
    val mainDisplay:  LiveData<String> = Transformations.map(fsm.buffer){
        it?.toString() ?: "0"
    }
    val memoryDisplay:  LiveData<String> = Transformations.map(fsm.memory){
        if (it.isNullOrEmpty()) ""
        else "M: $it"
    }
    val historyDisplay:  LiveData<String> = Transformations.map(fsm.history){
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
            Buttons.ZERO-> fsm.setSymbol(Symbols.ZERO)
            Buttons.ONE-> fsm.setSymbol(Symbols.ONE)
            Buttons.TWO-> fsm.setSymbol(Symbols.TWO)
            Buttons.THREE-> fsm.setSymbol(Symbols.THREE)
            Buttons.FOUR-> fsm.setSymbol(Symbols.FOUR)
            Buttons.FIVE-> fsm.setSymbol(Symbols.FIVE)
            Buttons.SIX-> fsm.setSymbol(Symbols.SIX)
            Buttons.SEVEN-> fsm.setSymbol(Symbols.SEVEN)
            Buttons.EIGHT->  fsm.setSymbol(Symbols.EIGHT)
            Buttons.NINE-> fsm.setSymbol(Symbols.NINE)
            Buttons.DOT-> fsm.setSymbol(Symbols.DOT)
            Buttons.NEGATIVE -> fsm.negative()
            Buttons.CLEAR-> fsm.clear()
            Buttons.BACKSPACE-> fsm.backspace()
            Buttons.RESULT-> fsm.result()
            Buttons.PLUS-> fsm.setOperator(Operators.PLUS)
            Buttons.MINUS-> fsm.setOperator(Operators.MINUS)
            Buttons.MULTIPLY-> fsm.setOperator(Operators.MULTIPLY)
            Buttons.DIVIDE-> fsm.setOperator(Operators.DIVIDE)
            Buttons.PERCENT-> fsm.percent()
            Buttons.SQR-> fsm.setOperator(Operators.SQR)
            Buttons.SQRT-> fsm.setOperator(Operators.SQRT)
            Buttons.SIN-> fsm.setOperator(Operators.SIN)
            Buttons.COS-> fsm.setOperator(Operators.COS)
            Buttons.TAN-> fsm.setOperator(Operators.TAN)
            Buttons.M_CLEAR-> fsm.memoryClear()
            Buttons.M_PLUS-> fsm.memoryPlus()
            Buttons.M_MINUS-> fsm.memoryMinus()
            Buttons.M_RESTORE-> fsm.memoryRestore()
        }
    }
    fun clearHistory() {
        dao.deleteAll()
        fsm.historyClear()
    }

}