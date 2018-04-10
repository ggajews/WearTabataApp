package com.grzesiek.tabatatimer.timer.model

import com.grzesiek.tabatatimer.timer.Round
import com.grzesiek.tabatatimer.timer.RoundState
import com.grzesiek.tabatatimer.utils.SchedulersProvider
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class RoundsExecutor @Inject constructor(private val schedulersProvider: SchedulersProvider) {

    fun start(rounds: List<Round>): Observable<RoundState> = Observable
            .fromIterable(rounds)
            .concatMap { round ->
                Observable
                        .intervalRange(0L,
                                round.duration + 1L,
                                0L, 1L,
                                TimeUnit.SECONDS,
                                schedulersProvider.computation()
                        )
                        .map { RoundState(round, round.duration - it.toInt()) }
                        .filter { it.leftTime != 0 }
            }
}

