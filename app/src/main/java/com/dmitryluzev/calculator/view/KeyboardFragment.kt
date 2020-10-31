package com.dmitryluzev.calculator.view

import android.content.Context
import android.os.Bundle
import android.view.HapticFeedbackConstants
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import com.dmitryluzev.calculator.R
import com.dmitryluzev.calculator.app.Pref
import com.dmitryluzev.calculator.app.Sound
import com.dmitryluzev.calculator.core.Symbols
import com.dmitryluzev.calculator.operations.Operations
import com.dmitryluzev.calculator.view.main.MainViewModel

class KeyboardFragment : Fragment() {
    private lateinit var pref: Pref
    private lateinit var sound: Sound
    private val vm: MainViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        pref = Pref.getInstance(requireActivity().application)
        sound = Sound.getInstance(requireActivity().application)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_keyboard, container, false)
        initButtons(view)
        return view
    }
    private fun initButtons(view: View){
        view.findViewById<Button>(R.id.btn_0).setOnClickListener { vm.symbol(Symbols.ZERO); fb(it) }
        view.findViewById<Button>(R.id.btn_1).setOnClickListener { vm.symbol(Symbols.ONE); fb(it) }
        view.findViewById<Button>(R.id.btn_2).setOnClickListener { vm.symbol(Symbols.TWO); fb(it) }
        view.findViewById<Button>(R.id.btn_3).setOnClickListener { vm.symbol(Symbols.THREE); fb(it) }
        view.findViewById<Button>(R.id.btn_4).setOnClickListener { vm.symbol(Symbols.FOUR); fb(it) }
        view.findViewById<Button>(R.id.btn_5).setOnClickListener { vm.symbol(Symbols.FIVE); fb(it) }
        view.findViewById<Button>(R.id.btn_6).setOnClickListener { vm.symbol(Symbols.SIX); fb(it) }
        view.findViewById<Button>(R.id.btn_7).setOnClickListener { vm.symbol(Symbols.SEVEN); fb(it) }
        view.findViewById<Button>(R.id.btn_8).setOnClickListener { vm.symbol(Symbols.EIGHT); fb(it) }
        view.findViewById<Button>(R.id.btn_9).setOnClickListener { vm.symbol(Symbols.NINE); fb(it) }
        view.findViewById<Button>(R.id.btn_dot).setOnClickListener { vm.symbol(Symbols.DOT); fb(it) }
        view.findViewById<Button>(R.id.btn_negative).setOnClickListener { vm.negative(); fb(it) }

        view.findViewById<Button>(R.id.btn_clear).setOnClickListener { vm.clearCalc(); fb(it) }
        view.findViewById<Button>(R.id.btn_backspace).apply {
            setOnClickListener { vm.backspace(); fb(it) }
            setOnLongClickListener {vm.clearBuffer(); fb(it); return@setOnLongClickListener true}
        }
        view.findViewById<Button>(R.id.btn_result).setOnClickListener { vm.result(); fb(it) }

        view.findViewById<Button>(R.id.btn_m_clear).setOnClickListener { vm.memoryClear(); fb(it) }
        view.findViewById<Button>(R.id.btn_m_add).setOnClickListener { vm.memoryAdd(); fb(it) }
        view.findViewById<Button>(R.id.btn_m_subtract).setOnClickListener { vm.memorySubtract(); fb(it) }
        view.findViewById<Button>(R.id.btn_m_restore).setOnClickListener { vm.memoryRestore(); fb(it) }

        view.findViewById<Button>(R.id.btn_add).setOnClickListener { vm.operation(Operations.Add()); fb(it) }
        view.findViewById<Button>(R.id.btn_subtract).setOnClickListener { vm.operation(Operations.Subtract()); fb(it) }
        view.findViewById<Button>(R.id.btn_multiply).setOnClickListener { vm.operation(Operations.Multiply()); fb(it) }
        view.findViewById<Button>(R.id.btn_divide).setOnClickListener { vm.operation(Operations.Divide()); fb(it) }
        view.findViewById<Button>(R.id.btn_percent).setOnClickListener { vm.percent(); fb(it) }
    }
    private fun fb(view: View){
        if (pref.sound){
            sound.button()
        }
        if (pref.haptic){
            view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_PRESS)
        }
    }
}