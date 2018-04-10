package com.grzesiek.tabatatimer

import com.grzesiek.tabatatimer.settings.SettingsActivityModule
import com.grzesiek.tabatatimer.settings.view.SettingsActivity
import com.grzesiek.tabatatimer.timer.TabataActivityModule
import com.grzesiek.tabatatimer.timer.view.TabataActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector(modules = arrayOf(SettingsActivityModule::class))
    abstract fun settingsActivity(): SettingsActivity

    @ContributesAndroidInjector(modules = arrayOf(TabataActivityModule::class))
    abstract fun tabataActivity(): TabataActivity
}
