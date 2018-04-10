package com.grzesiek.tabatatimer.settings.presenter

import com.grzesiek.tabatatimer.settings.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import javax.inject.Inject

class SettingsPresenter @Inject constructor(private val view: View, private val model: Model) : Presenter {
    private val compositeDisposable = CompositeDisposable()

    override fun start() {
        compositeDisposable += model
                .getRoundsConfiguration()
                .map {
                    listOf(
                            SettingsItem(SettingType.ROUNDS, it.rounds),
                            SettingsItem(SettingType.WORK_TIME, it.workTime),
                            SettingsItem(SettingType.REST_TIME, it.restTime))
                }
                .subscribe { view.showSettings(it) }

        compositeDisposable += view
                .decrements()
                .subscribe { model.dispatchAction(Action.Decrement(it)) }

        compositeDisposable += view
                .increments()
                .subscribe { model.dispatchAction(Action.Increment(it)) }
    }

    override fun stop() {
        compositeDisposable.clear()
    }
}
