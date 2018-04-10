package com.grzesiek.tabatatimer.persistence

import io.reactivex.Single

data class RoundsConfiguration(val rounds: Int, val workTime: Int, val restTime: Int)

interface RoundsConfigurationRepository {
    fun fetchRoundsConfig(): Single<RoundsConfiguration>
    fun saveRoundsConfig(roundsConfiguration: RoundsConfiguration)
}
