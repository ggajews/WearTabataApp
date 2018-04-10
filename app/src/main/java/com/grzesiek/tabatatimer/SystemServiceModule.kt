package com.grzesiek.tabatatimer

import android.content.Context
import android.os.Vibrator
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SystemServiceModule {
    @Provides
    @Singleton
    fun provideVibrator(context: Context): Vibrator = context.getSystemService(Vibrator::class.java)
}
