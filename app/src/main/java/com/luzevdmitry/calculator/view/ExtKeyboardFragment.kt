package com.luzevdmitry.calculator.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.luzevdmitry.calculator.R
import com.luzevdmitry.calculator.app.AppManager
import com.luzevdmitry.calculator.databinding.FragmentExtKeyboardBinding
import com.luzevdmitry.calculator.viewmodel.MainViewModel

class ExtKeyboardFragment : Fragment() {
    private val vm: MainViewModel by activityViewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentExtKeyboardBinding>(inflater, R.layout.fragment_ext_keyboard, container, false)
        binding.vm = vm
        binding.appManager = AppManager
        return binding.root
    }
}