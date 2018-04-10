package com.grzesiek.tabatatimer.timer

import io.reactivex.Observable

interface View {
    fun setRoundName(roundType: RoundType)
    fun showTimeLeft(timeLeft: Int)
    fun setBackgroundColor(roundType: RoundType)
    fun showRoundsCount(roundCount: Int, totalRounds: Int)
}

interface Presenter {
    fun start()
    fun stop()
}

interface Model {
    fun runTabata(): Observable<TabataState>
}

interface ActivityController {
    fun finish()
}

data class Round(val roundType: RoundType, val duration: Int)

data class RoundState(val round: Round, val leftTime: Int)

enum class RoundType {
    PREPARATION, WORK, REST, COOL_DOWN
}

data class TabataState(
        val roundType: RoundType,
        val timeLeft: Int,
        val roundCount: Int,
        val totalRounds: Int)
