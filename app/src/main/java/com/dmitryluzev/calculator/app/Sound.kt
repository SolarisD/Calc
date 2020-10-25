package com.dmitryluzev.calculator.app

import android.app.Application
import android.media.AudioAttributes
import android.media.SoundPool
import com.dmitryluzev.calculator.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Sound @Inject constructor(application: Application) {
    private var sp: SoundPool
    private var buttonSoundId: Int
    init {
        val attr = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()
        sp = SoundPool.Builder()
            .setAudioAttributes(attr)
            .build()

        buttonSoundId = sp.load(application, R.raw.button_click_sfx, 1)
    }
    fun button(){
        sp.stop(buttonSoundId)
        sp.play(buttonSoundId, 0.1f, 0.1f, 1, 0, 1.0f)
    }
}