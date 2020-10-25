package com.dmitryluzev.calculator.di.components

import com.dmitryluzev.calculator.di.scopes.ActivityScope
import com.dmitryluzev.calculator.view.KeyboardFragment
import com.dmitryluzev.calculator.view.MainActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface MainComponent {
    @Subcomponent.Factory
    interface Factory{
        fun create(): MainComponent
    }

    fun inject(target: MainActivity)
    fun inject(target: KeyboardFragment)
}