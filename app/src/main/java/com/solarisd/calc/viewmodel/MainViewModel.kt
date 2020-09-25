package com.solarisd.calc.viewmodel

import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.lifecycle.*
import com.solarisd.calc.R
import com.solarisd.calc.app.AppManager
import com.solarisd.calc.core.*
import com.solarisd.calc.model.*
import java.lang.Exception

class MainViewModel(private val app: Application): AndroidViewModel(app){
    private var mp: MediaPlayer? = null
    private val c = Core(DB.getInstance(app).dao())
    val bufferDisplay:  LiveData<String> = Transformations.map(c.bufferOut){
        it?.toString() ?: "0"
    }
    val memoryDisplay:  LiveData<String> = Transformations.map(c.memoryOut){
        if (it.isNullOrEmpty()) ""
        else "M: $it"
    }
    val operationDisplay:  LiveData<String> = Transformations.map(c.operationOut){
        it?.toString() ?: ""
    }
    fun buttonPressed(button: Buttons){
        //vibrate()
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
    fun pasteFromClipboard(value: String): String?{
        return try {
            val double = value.toDoubleFromDisplay()
            c.setBufferValue(double)
            double.toDisplayString()
        } catch (e: Exception){
            null
        }
    }
    fun clearInput(){
        c.clearBuffer()
    }
    private fun sound(){
        if (AppManager.sound){
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
}