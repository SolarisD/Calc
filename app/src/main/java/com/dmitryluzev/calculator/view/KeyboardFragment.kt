package com.dmitryluzev.calculator.view

import android.content.Context
import android.os.Bundle
import android.view.HapticFeedbackConstants
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.dmitryluzev.calculator.R
import com.dmitryluzev.calculator.app.Sound
import com.dmitryluzev.calculator.app.Pref
import com.dmitryluzev.calculator.core.Symbols
import com.dmitryluzev.calculator.operations.Operations
import com.dmitryluzev.calculator.viewmodel.MainViewModel
import javax.inject.Inject

class KeyboardFragment : Fragment() {
    @Inject lateinit var pref: Pref
    @Inject lateinit var sound: Sound
    @Inject lateinit var vm: MainViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).mainComponent.inject(this)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_keyboard, container, false)
        initButtons(view)
        return view
    }
    private fun initButtons(view: View){
        view.findViewById<Button>(R.id.btn_0).setOnClickListener { vm.symbol(Symbols.ZERO); feedback(it) }
        view.findViewById<Button>(R.id.btn_1).setOnClickListener { vm.symbol(Symbols.ONE); feedback(it) }
        view.findViewById<Button>(R.id.btn_2).setOnClickListener { vm.symbol(Symbols.TWO); feedback(it) }
        view.findViewById<Button>(R.id.btn_3).setOnClickListener { vm.symbol(Symbols.THREE); feedback(it) }
        view.findViewById<Button>(R.id.btn_4).setOnClickListener { vm.symbol(Symbols.FOUR); feedback(it) }
        view.findViewById<Button>(R.id.btn_5).setOnClickListener { vm.symbol(Symbols.FIVE); feedback(it) }
        view.findViewById<Button>(R.id.btn_6).setOnClickListener { vm.symbol(Symbols.SIX); feedback(it) }
        view.findViewById<Button>(R.id.btn_7).setOnClickListener { vm.symbol(Symbols.SEVEN); feedback(it) }
        view.findViewById<Button>(R.id.btn_8).setOnClickListener { vm.symbol(Symbols.EIGHT); feedback(it) }
        view.findViewById<Button>(R.id.btn_9).setOnClickListener { vm.symbol(Symbols.NINE); feedback(it) }
        view.findViewById<Button>(R.id.btn_dot).setOnClickListener { vm.symbol(Symbols.DOT); feedback(it) }
        view.findViewById<Button>(R.id.btn_negative).setOnClickListener { vm.negative(); feedback(it) }

        view.findViewById<Button>(R.id.btn_clear).setOnClickListener { vm.clear(); feedback(it) }
        view.findViewById<Button>(R.id.btn_backspace).apply {
            setOnClickListener { vm.backspace(); feedback(it) }
            setOnLongClickListener {vm.clearBuffer(); feedback(it); return@setOnLongClickListener true}
        }
        view.findViewById<Button>(R.id.btn_result).setOnClickListener { vm.result(); feedback(it) }

        view.findViewById<Button>(R.id.btn_m_clear).setOnClickListener { vm.memoryClear(); feedback(it) }
        view.findViewById<Button>(R.id.btn_m_add).setOnClickListener { vm.memoryAdd(); feedback(it) }
        view.findViewById<Button>(R.id.btn_m_subtract).setOnClickListener { vm.memorySubtract(); feedback(it) }
        view.findViewById<Button>(R.id.btn_m_restore).setOnClickListener { vm.memoryRestore(); feedback(it) }

        view.findViewById<Button>(R.id.btn_add).setOnClickListener { vm.operation(Operations.Add()); feedback(it) }
        view.findViewById<Button>(R.id.btn_subtract).setOnClickListener { vm.operation(Operations.Subtract()); feedback(it) }
        view.findViewById<Button>(R.id.btn_multiply).setOnClickListener { vm.operation(Operations.Multiply()); feedback(it) }
        view.findViewById<Button>(R.id.btn_divide).setOnClickListener { vm.operation(Operations.Divide()); feedback(it) }
        view.findViewById<Button>(R.id.btn_percent).setOnClickListener { vm.percent(); feedback(it) }
    }
    private fun feedback(view: View){
        if (pref.sound){
            sound.button()
        }
        if (pref.haptic){
            view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_PRESS)
        }
    }
}