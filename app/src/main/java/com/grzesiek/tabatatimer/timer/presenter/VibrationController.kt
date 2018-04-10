package com.grzesiek.tabatatimer.timer.presenter

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import javax.inject.Inject

interface VibrationController {
    fun vibrate()
}

class AndroidVibrationController @Inject constructor(private val vibrator: Vibrator) : VibrationController {
    @Suppress("DEPRECATION")
    override fun vibrate() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(VIBRATION_DURATION, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(VIBRATION_DURATION)
        }
    }

    companion object {
        const val VIBRATION_DURATION = 500L
    }
}
