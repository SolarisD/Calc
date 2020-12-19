package com.dmitryluzev.testapp.view.calculator

import android.view.HapticFeedbackConstants
import android.view.View
import androidx.lifecycle.*
import com.dmitryluzev.basic.SymbolBuffer
import com.dmitryluzev.testapp.app.PrefManager
import com.dmitryluzev.testapp.app.Sound
import com.dmitryluzev.testapp.model.Record
import com.dmitryluzev.testapp.model.Repo
import com.dmitryluzev.core.buffer.Converter
import com.dmitryluzev.core.buffer.Symbols
import com.dmitryluzev.core.CalculatorImpl
import com.dmitryluzev.core.operations.Operation
import com.dmitryluzev.core.operations.OperationFactory
import java.util.*

class CalculatorViewModel(private val calc: CalculatorImpl, private val repo: Repo, private val prefManager: PrefManager, private val sound: Sound) : ViewModel(){

    private val historyDate: MutableLiveData<Date> = MutableLiveData(prefManager.restoreDisplayHistoryDate())
    private val filteredHistory: LiveData<List<Record>> = Transformations.switchMap(historyDate){ repo.getHistoryFromDate(it) }
    private val activeOperation = MutableLiveData<Operation>()
    val adapterList: MediatorLiveData<List<Record>> = MediatorLiveData()

    private val _buffer = MutableLiveData<String>()
    val buffer: LiveData<String>
        get() = _buffer
    private val _memory = MutableLiveData<String>()
    val memory: LiveData<String>
        get() = _memory


    private val symbolBuffer = SymbolBuffer()

    init {
        adapterList.addSource(filteredHistory){
            adapterList.value = joinOperations(filteredHistory.value, activeOperation.value)
        }
        adapterList.addSource(activeOperation){
            adapterList.value = joinOperations(filteredHistory.value, activeOperation.value)
        }
        calc.setOnOperationComplete{
            repo.saveToHistory(it)
        }
        updateDisplays()
    }
    
    private fun joinOperations(list: List<Record>?, operation: Operation?): List<Record>{
        val out: MutableList<Record> = list?.toMutableList() ?: mutableListOf()
        operation?.let {
            if(out.size > 0){
                if (out.last().op == it) {out.removeLast()}
            }
            out.add(Record(date = Date(System.currentTimeMillis()), op = it, id = Int.MIN_VALUE))

        }
        return out
    }

    fun saveState(){
        prefManager.saveState(calc.get())
        historyDate.value?.let { prefManager.saveDisplayHistoryDate(it) }
    }
    fun pasteFromClipboard(value: String): String?{
        if (symbolBuffer.set(value)){
            updateDisplays()
            return symbolBuffer.get()
        }
        return null
    }
    fun buttonEvents(view: View, button: Buttons): Boolean{
        haptics(view)
        when(button){
            Buttons.BUFFER_CLEAR -> symbolBuffer.clear()
            Buttons.CALC_CLEAR -> {symbolBuffer.clear(); calc.clear()}
            Buttons.ALL_CLEAR -> {symbolBuffer.clear(); calc.clear(); historyDate.value = Date(System.currentTimeMillis())}

            Buttons.BACKSPACE -> symbolBuffer.backspace()
            Buttons.DOT -> symbolBuffer.symbol(SymbolBuffer.DELIMITER)
            Buttons.NEGATIVE -> symbolBuffer.symbol(SymbolBuffer.NEGATIVE)
            Buttons.ZERO -> symbolBuffer.symbol(SymbolBuffer.ZERO)
            Buttons.ONE -> symbolBuffer.symbol(SymbolBuffer.ONE)
            Buttons.TWO -> symbolBuffer.symbol(SymbolBuffer.TWO)
            Buttons.THREE -> symbolBuffer.symbol(SymbolBuffer.THREE)
            Buttons.FOUR -> symbolBuffer.symbol(SymbolBuffer.FOUR)
            Buttons.FIVE -> symbolBuffer.symbol(SymbolBuffer.FIVE)
            Buttons.SIX -> symbolBuffer.symbol(SymbolBuffer.SIX)
            Buttons.SEVEN -> symbolBuffer.symbol(SymbolBuffer.SEVEN)
            Buttons.EIGHT -> symbolBuffer.symbol(SymbolBuffer.EIGHT)
            Buttons.NINE -> symbolBuffer.symbol(SymbolBuffer.NINE)

            Buttons.MEM_CLEAR -> calc.clearMem()
            Buttons.MEM_ADD -> calc.addMem()
            Buttons.MEM_SUB -> calc.subMem()
            Buttons.MEM_RESTORE -> calc.restoreMem()

            Buttons.PERCENT -> calc.percent()
            Buttons.DIV -> calc.operation(OperationFactory.DIVIDE_ID)
            Buttons.MUL -> calc.operation(OperationFactory.MULTIPLY_ID)
            Buttons.SUB -> calc.operation(OperationFactory.SUBTRACT_ID)
            Buttons.ADD -> calc.operation(OperationFactory.ADD_ID)
            Buttons.RESULT -> calc.result()
        }
        updateDisplays()
        return true
    }
    private fun updateDisplays(){
        symbolBuffer.get().let {
            if (it.isNotEmpty()) _buffer.value = it;
            else _buffer.value = "0"
        }

    }
    private fun haptics(view: View? = null){
        prefManager.livePref.value?.let {
            if (it.haptic) view?.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
            if (it.sound) sound.button()
        }
    }
}

class CalculatorViewModelFactory(private val calc: CalculatorImpl, private val repo: Repo, private val prefManager: PrefManager, private val sound: Sound): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalculatorViewModel::class.java)){
            return CalculatorViewModel(calc, repo, prefManager, sound) as T
        }

        throw IllegalArgumentException("ViewModel class isn't KeyboardViewModel")
    }
}