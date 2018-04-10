package com.grzesiek.tabatatimer.timer.model

import com.grzesiek.tabatatimer.persistence.RoundsConfiguration
import com.grzesiek.tabatatimer.timer.Round
import com.grzesiek.tabatatimer.timer.RoundType
import javax.inject.Inject

class RoundsBuilder @Inject constructor() {

    fun build(roundsConfiguration: RoundsConfiguration): List<Round> {
        val roundsList = mutableListOf<Round>()
        roundsList.add(Round(RoundType.PREPARATION, PREPARATION_TIME))
        (0 until roundsConfiguration.rounds).forEach { _ ->
            roundsList.add(Round(RoundType.WORK, roundsConfiguration.workTime))
            roundsList.add(Round(RoundType.REST, roundsConfiguration.restTime))
        }
        roundsList.add(Round(RoundType.COOL_DOWN, COOL_DOWN_TIME))
        return roundsList.toList()
    }

    companion object {
        const val PREPARATION_TIME = 10
        const val COOL_DOWN_TIME = 10
    }
}
