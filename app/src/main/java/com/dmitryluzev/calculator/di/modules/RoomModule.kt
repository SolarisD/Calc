package com.dmitryluzev.calculator.di.modules

import android.app.Application
import androidx.room.Room
import com.dmitryluzev.calculator.model.DB
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {
    @Singleton
    @Provides
    fun providesDB(application: Application) = Room.databaseBuilder(application, DB::class.java, "calc_db")
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun providesDao(db: DB) = db.dao()
}