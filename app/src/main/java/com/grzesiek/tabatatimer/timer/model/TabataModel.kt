package com.grzesiek.tabatatimer.timer.model

import com.grzesiek.tabatatimer.persistence.RoundsConfigurationRepository
import com.grzesiek.tabatatimer.timer.Model
import com.grzesiek.tabatatimer.timer.RoundType
import com.grzesiek.tabatatimer.timer.TabataState
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.zipWith
import javax.inject.Inject

class TabataModel @Inject constructor(private val roundsExecutor: RoundsExecutor,
                                      private val roundsBuilder: RoundsBuilder,
                                      private val roundsConfigurationProvider: RoundsConfigurationRepository) : Model {

    override fun runTabata(): Observable<TabataState> {
        return roundsConfigurationProvider
                .fetchRoundsConfig()
                .map { roundsBuilder.build(it) }
                .toObservable()
                .switchMap { roundsList ->
                    val roundsObservable = roundsExecutor
                            .start(roundsList)
                            .share()

                    val roundsCount = roundsList.filter { it.roundType == RoundType.WORK }.size

                    val roundsCounter = roundsObservable
                            .map { it.round.roundType }
                            .distinctUntilChanged()
                            .filter { it == RoundType.WORK }
                            .zipWith(Observable.range(1, roundsCount)) { _, i -> i }

                    Observables.combineLatest(
                            roundsObservable,
                            roundsCounter.startWith(0)
                    ) { roundState, counter -> TabataState(roundState.round.roundType, roundState.leftTime, counter, roundsCount) }
                }
    }
}
