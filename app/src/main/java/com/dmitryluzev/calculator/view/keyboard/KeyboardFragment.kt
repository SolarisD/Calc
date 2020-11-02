package com.dmitryluzev.calculator.view.keyboard

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.dmitryluzev.calculator.R
import com.dmitryluzev.calculator.app.Pref
import com.dmitryluzev.calculator.app.Sound
import com.dmitryluzev.calculator.core.Calculator
import com.dmitryluzev.calculator.core.operations.ID
import com.dmitryluzev.calculator.databinding.FragmentKeyboardBinding

class KeyboardFragment : Fragment() {
   private lateinit var vm: KeyboardViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val application = requireNotNull(this.activity).application
        val pref = Pref.getInstance(application)
        val sound = Sound.getInstance(application)
        val calculator = Calculator.getInstance()
        vm = ViewModelProvider(this, KeyboardViewModelFactory(calculator, pref, sound)).get(KeyboardViewModel::class.java)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentKeyboardBinding>(layoutInflater, R.layout.fragment_keyboard, container, false)
        binding.vm = vm
        return binding.root
    }
}



