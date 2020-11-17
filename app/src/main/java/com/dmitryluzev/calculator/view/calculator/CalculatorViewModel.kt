package com.dmitryluzev.calculator.view.calculator

import android.view.HapticFeedbackConstants
import android.view.View
import androidx.lifecycle.*
import com.dmitryluzev.calculator.app.Pref
import com.dmitryluzev.calculator.app.Sound
import com.dmitryluzev.calculator.model.Record
import com.dmitryluzev.calculator.model.Repo
import com.dmitryluzev.core.Calculator
import com.dmitryluzev.core.Symbols
import com.dmitryluzev.core.operations.OperationFactory
import java.util.*

class CalculatorViewModel(private val calc: Calculator, private val repo: Repo, private val pref: Pref, private val sound: Sound) : ViewModel(){
    private val historyDate: MutableLiveData<Date> = MutableLiveData(pref.restoreDisplayHistoryDate())
    val historyDisplay: LiveData<List<Record>> = Transformations.switchMap(historyDate){ repo.getHistoryFromDate(it) }
    val aluDisplay: LiveData<String> = Transformations.map(calc.aluOut){ it?.toString() }
    val bufferDisplay: LiveData<String> = Transformations.map(calc.bufferOut){ it?.toString() }
    val memoryDisplay: LiveData<String> = Transformations.map(calc.memoryDisplay){ if (it == null) "" else "M: $it" }
    init {
        if (!calc.initialized){
            calc.setState(pref.restoreState())
        }
        calc.setOnResultReadyListener {
            repo.saveToHistory(it)
        }
    }
    fun saveState(){
        pref.saveState(calc.getState())
        historyDate.value?.let { pref.saveDisplayHistoryDate(it) }
    }
    fun pasteFromClipboard(value: String): String? = calc.pasteFromClipboard(value)
    fun buttonEvents(view: View, button: Buttons): Boolean{
        haptics(view)
        when(button){
            Buttons.MEM_CLEAR -> calc.memoryClear()
            Buttons.MEM_ADD -> calc.memoryAdd()
            Buttons.MEM_SUB -> calc.memorySubtract()
            Buttons.MEM_RESTORE -> calc.memoryRestore()
            Buttons.CALC_CLEAR -> calc.clear()
            Buttons.ALL_CLEAR -> {calc.clear(); historyDate.value = Date(System.currentTimeMillis())}
            Buttons.BACKSPACE -> calc.backspace()
            Buttons.BUFFER_CLEAR -> calc.clearBuffer()
            Buttons.PERCENT -> calc.percent()
            Buttons.DIV -> calc.operation(OperationFactory.DIVIDE_ID)
            Buttons.MUL -> calc.operation(OperationFactory.MULTIPLY_ID)
            Buttons.SUB -> calc.operation(OperationFactory.SUBTRACT_ID)
            Buttons.ADD -> calc.operation(OperationFactory.ADD_ID)
            Buttons.RESULT -> calc.result()
            Buttons.DOT -> calc.symbol(Symbols.DOT)
            Buttons.NEGATIVE -> calc.negative()
            Buttons.ZERO -> calc.symbol(Symbols.ZERO)
            Buttons.ONE -> calc.symbol(Symbols.ONE)
            Buttons.TWO -> calc.symbol(Symbols.TWO)
            Buttons.THREE -> calc.symbol(Symbols.THREE)
            Buttons.FOUR -> calc.symbol(Symbols.FOUR)
            Buttons.FIVE -> calc.symbol(Symbols.FIVE)
            Buttons.SIX -> calc.symbol(Symbols.SIX)
            Buttons.SEVEN -> calc.symbol(Symbols.SEVEN)
            Buttons.EIGHT -> calc.symbol(Symbols.EIGHT)
            Buttons.NINE -> calc.symbol(Symbols.NINE)
        }
        return true
    }

    private fun haptics(view: View? = null){
        if (pref.haptic) view?.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
        if (pref.sound) sound.button()
    }
}

class CalculatorViewModelFactory(private val calc: Calculator, private val repo: Repo, private val pref: Pref, private val sound: Sound): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalculatorViewModel::class.java)){
            return CalculatorViewModel(calc, repo, pref, sound) as T
        }

        throw IllegalArgumentException("ViewModel class isn't KeyboardViewModel")
    }
}