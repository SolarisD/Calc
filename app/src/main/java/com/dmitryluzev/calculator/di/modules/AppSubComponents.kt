package com.dmitryluzev.calculator.di.modules

import com.dmitryluzev.calculator.di.components.HistoryComponent
import com.dmitryluzev.calculator.di.components.MainComponent
import dagger.Module

@Module(subcomponents = [MainComponent::class, HistoryComponent::class])
class AppSubComponents