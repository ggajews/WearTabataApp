package com.grzesiek.tabatatimer.timer

import com.grzesiek.tabatatimer.timer.model.TabataModel
import com.grzesiek.tabatatimer.timer.presenter.AndroidVibrationController
import com.grzesiek.tabatatimer.timer.presenter.TabataActivityController
import com.grzesiek.tabatatimer.timer.presenter.TabataPresenter
import com.grzesiek.tabatatimer.timer.presenter.VibrationController
import com.grzesiek.tabatatimer.timer.view.TabataActivity
import dagger.Binds
import dagger.Module

@Module
abstract class TabataActivityModule {

    @Binds
    abstract fun bindPresenter(tabataPresenter: TabataPresenter): Presenter

    @Binds
    abstract fun bindModel(tabataModel: TabataModel): Model

    @Binds
    abstract fun bindView(timerActivity: TabataActivity): View

    @Binds
    abstract fun bindVibrationController(androidVibrationController: AndroidVibrationController): VibrationController

    @Binds
    abstract fun bindActivityController(tabataActivityController: TabataActivityController): ActivityController
}
