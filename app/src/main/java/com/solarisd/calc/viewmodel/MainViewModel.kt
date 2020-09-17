package com.solarisd.calc.viewmodel

import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.lifecycle.*
import com.solarisd.calc.R
import com.solarisd.calc.core.*
import com.solarisd.calc.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val app: Application): AndroidViewModel(app){
    companion object{
        private const val BUFFER_STATE_KEY = "buffer_state"
        private const val MEMORY_STATE_KEY = "memory_state"
    }
    private val v = app.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    private var mp: MediaPlayer? = null
    private val dao: Dao = DB.getInstance(app).dao()
    val bfr = PrefManager.getString(BUFFER_STATE_KEY)
    val mem = PrefManager.getString(MEMORY_STATE_KEY)
    private val c = Core(State(bfr, mem))
    val bufferDisplay:  LiveData<String> = Transformations.map(c.buffer){
        PrefManager.setString(BUFFER_STATE_KEY, it)
        it?.toString() ?: "0"
    }
    val memoryDisplay:  LiveData<String> = Transformations.map(c.memory){
        PrefManager.setString(MEMORY_STATE_KEY, it)
        if (it.isNullOrEmpty()) ""
        else "M: $it"
    }
    val operationDisplay:  LiveData<String> = Transformations.map(c.operation){
        //SAVE DATA TO DB
        it?.let {
            if (it.isComplete){
                viewModelScope.launch(Dispatchers.IO) {
                    dao.insert(Record(expression = it.toString()))
                }
            }
        }
        //POST TO DISPLAY
        it?.toString() ?: ""
    }
    val historyRecords: LiveData<List<Record>> = dao.getLiveRecords()
    fun buttonPressed(button: Buttons){
        vibrate()
        sound()
        when(button) {
            Buttons.ZERO-> c.symbol('0')
            Buttons.ONE-> c.symbol('1')
            Buttons.TWO-> c.symbol('2')
            Buttons.THREE-> c.symbol('3')
            Buttons.FOUR-> c.symbol('4')
            Buttons.FIVE-> c.symbol('5')
            Buttons.SIX-> c.symbol('6')
            Buttons.SEVEN-> c.symbol('7')
            Buttons.EIGHT->  c.symbol('8')
            Buttons.NINE-> c.symbol('9')
            Buttons.DOT-> c.symbol('.')
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
    }
    private fun sound(){
        if (PrefManager.sound){
            if (mp != null){
                if(mp!!.isPlaying) {
                    mp!!.stop()
                    mp!!.reset()
                    mp!!.release()
                    mp = null
                }
            }
            mp = MediaPlayer.create(app, R.raw.button_click_sfx)
            mp?.start()
        }
    }
    private fun vibrate(){
        if (PrefManager.vibro){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                v.vibrate(50);
            }
        }
    }
    fun saveState(state: State){
        /*PrefManager.pref.edit()
            .putString(BUFFER_STATE_KEY, state.buffer)
            .putString(MEMORY_STATE_KEY, state.memory)
            .commit()*/
    }
    fun restoreState(): State{
        return State(/*PrefManager.pref.getString(BUFFER_STATE_KEY, null), PrefManager.pref.getString(MEMORY_STATE_KEY, null)*/)
    }
}