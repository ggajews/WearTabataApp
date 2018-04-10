package com.grzesiek.tabatatimer.timer.presenter

import com.grzesiek.tabatatimer.timer.ActivityController
import com.grzesiek.tabatatimer.timer.Model
import com.grzesiek.tabatatimer.timer.Presenter
import com.grzesiek.tabatatimer.timer.View
import com.grzesiek.tabatatimer.utils.SchedulersProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import javax.inject.Inject

class TabataPresenter @Inject constructor(private val view: View,
                                          private val model: Model,
                                          private val vibrationController: VibrationController,
                                          private val activityController: ActivityController,
                                          private val schedulersProvider: SchedulersProvider) : Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun start() {
        val tabataObservable = model
                .runTabata()
                .observeOn(schedulersProvider.ui())
                .share()

        val roundChanges = tabataObservable
                .map { it.roundType }
                .distinctUntilChanged()

        compositeDisposable +=
                tabataObservable
                        .subscribe({ view.showTimeLeft(it.timeLeft) },
                                {},
                                { activityController.finish() })

        compositeDisposable +=
                roundChanges
                        .subscribe {
                            view.setRoundName(it)
                            view.setBackgroundColor(it)
                            vibrationController.vibrate()
                        }

        compositeDisposable +=
                tabataObservable
                        .distinctUntilChanged { tabataState -> tabataState.roundCount }
                        .subscribe {
                            view.showRoundsCount(it.roundCount, it.totalRounds)
                        }
    }

    override fun stop() {
        compositeDisposable.clear()
    }
}
