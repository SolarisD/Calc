package com.dmitryluzev.calculator.di.components

import android.app.Application
import com.dmitryluzev.calculator.di.modules.AppSubComponents
import com.dmitryluzev.calculator.di.modules.RoomModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RoomModule::class, AppSubComponents::class])
interface AppComponent{
    @Component.Factory
    interface Factory{
        fun create(@BindsInstance application: Application): AppComponent
    }

    fun mainComponent(): MainComponent.Factory
    fun historyComponent(): HistoryComponent.Factory
}