package com.solarisd.calc.viewmodel

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.lifecycle.*
import androidx.preference.PreferenceManager
import com.solarisd.calc.model.Prefs
import com.solarisd.calc.core.Calculator
import com.solarisd.calc.core.enums.Buttons
import com.solarisd.calc.core.enums.Operators
import com.solarisd.calc.core.enums.Symbols
import com.solarisd.calc.model.DB
import com.solarisd.calc.model.Dao
import com.solarisd.calc.model.History
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application){
    private val c = Calculator()
    private val v = application.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    private val prefs: Prefs by lazy {
        Prefs(PreferenceManager.getDefaultSharedPreferences(application))
    }
    private val dao: Dao = DB.getInstance(application).dao()
    var vMode: Boolean = prefs.getVibroMode()
        set(value) {
            prefs.setVibroMode(value)
            field = value
        }
    var kMode: String = prefs.getKeyboardMode()
        set(value) {
            prefs.setKeyboardMode(value)
            field = value
        }
    val mainDisplay:  LiveData<String> = Transformations.map(c.value){
        it?.toString() ?: "0"
    }
    val memoryDisplay:  LiveData<String> = Transformations.map(c.memory){
        if (it.isNullOrEmpty()) ""
        else "M: $it"
    }
    val historyDisplay:  LiveData<String> = Transformations.map(c.history){
        it?.let {
            if (it.result!=null){
                viewModelScope.launch(Dispatchers.IO) {
                    dao.insert(History(op = it.op.print, a = it.a, b = it.b, result = it.result))
                }
            }
        }
        it?.toString() ?: ""
    }

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
            Buttons.PLUS-> c.operator(Operators.PLUS)
            Buttons.MINUS-> c.operator(Operators.MINUS)
            Buttons.MULTIPLY-> c.operator(Operators.MULTIPLY)
            Buttons.DIVIDE-> c.operator(Operators.DIVIDE)
            Buttons.PERCENT-> c.percent()
            Buttons.SQR-> c.operator(Operators.SQR)
            Buttons.SQRT-> c.operator(Operators.SQRT)
            Buttons.SIN-> c.operator(Operators.SIN)
            Buttons.COS-> c.operator(Operators.COS)
            Buttons.TAN-> c.operator(Operators.TAN)
            Buttons.M_CLEAR-> c.mClear()
            Buttons.M_PLUS-> c.mPlus()
            Buttons.M_MINUS-> c.mMinus()
            Buttons.M_RESTORE-> c.mRestore()
        }
    }
    private fun vibrate(){
        if (vMode){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                v.vibrate(50);
            }
        }
    }
}