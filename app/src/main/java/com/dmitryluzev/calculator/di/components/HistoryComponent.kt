package com.dmitryluzev.calculator.di.components

import com.dmitryluzev.calculator.di.scopes.ActivityScope
import com.dmitryluzev.calculator.view.HistoryActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface HistoryComponent {
    @Subcomponent.Factory
    interface Factory{
        fun create(): HistoryComponent
    }

    fun inject(target: HistoryActivity)
}