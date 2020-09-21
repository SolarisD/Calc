package com.solarisd.calc.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.solarisd.calc.R
import com.solarisd.calc.app.AppManager
import com.solarisd.calc.databinding.FragmentExtKeyboardBinding
import com.solarisd.calc.viewmodel.MainViewModel

class ExtKeyboardFragment : Fragment() {
    private val vm: MainViewModel by activityViewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentExtKeyboardBinding>(inflater, R.layout.fragment_ext_keyboard, container, false)
        binding.vm = vm
        binding.appManager = AppManager
        return binding.root
    }
}