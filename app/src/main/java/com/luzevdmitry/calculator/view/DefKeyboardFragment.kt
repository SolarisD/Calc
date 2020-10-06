package com.luzevdmitry.calculator.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.luzevdmitry.calculator.R
import com.luzevdmitry.calculator.app.AppManager
import com.luzevdmitry.calculator.databinding.FragmentDefKeyboardBinding
import com.luzevdmitry.calculator.viewmodel.MainViewModel

class DefKeyboardFragment : Fragment() {
    private val vm: MainViewModel by activityViewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentDefKeyboardBinding>(inflater, R.layout.fragment_def_keyboard, container, false)
        binding.vm = vm
        binding.appManager = AppManager
        binding.root.findViewById<Button>(R.id.btn_backspace)?.setOnLongClickListener {
            vm.clearInput()
            return@setOnLongClickListener true
        }
        return binding.root
    }
}