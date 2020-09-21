package com.solarisd.calc.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.solarisd.calc.R
import com.solarisd.calc.app.AppManager
import com.solarisd.calc.databinding.FragmentDefKeyboardBinding
import com.solarisd.calc.viewmodel.MainViewModel

class DefKeyboardFragment : Fragment() {
    private val vm: MainViewModel by activityViewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentDefKeyboardBinding>(inflater, R.layout.fragment_def_keyboard, container, false)
        binding.vm = vm
        binding.appManager = AppManager
        val viewId = binding.root.findViewById<Button>(R.id.btn_0)?.id
        return binding.root
    }
}