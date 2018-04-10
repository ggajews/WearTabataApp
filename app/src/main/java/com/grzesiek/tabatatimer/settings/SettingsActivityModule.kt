package com.grzesiek.tabatatimer.settings

import com.grzesiek.tabatatimer.settings.model.SettingsModel
import com.grzesiek.tabatatimer.settings.presenter.SettingsPresenter
import com.grzesiek.tabatatimer.settings.view.SettingsActivity
import dagger.Binds
import dagger.Module

@Module
abstract class SettingsActivityModule {

    @Binds
    abstract fun bindPresenter(settingsPresenter: SettingsPresenter): Presenter

    @Binds
    abstract fun bindModel(settingsModel: SettingsModel): Model

    @Binds
    abstract fun bindView(settingsActivity: SettingsActivity): View
}
