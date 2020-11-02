package com.dmitryluzev.calculator.app

import android.app.Application
import android.media.AudioAttributes
import android.media.SoundPool
import com.dmitryluzev.calculator.R

class Sound private constructor(application: Application) {
    companion object{
        private var instance: Sound? = null
        fun getInstance(application: Application): Sound {
            if (instance == null) {
                instance = Sound(application)
            }
            return instance!!
        }
    }
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