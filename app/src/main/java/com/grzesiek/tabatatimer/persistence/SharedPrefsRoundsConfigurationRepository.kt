package com.grzesiek.tabatatimer.persistence

import android.content.Context
import com.grzesiek.tabatatimer.settings.SettingType
import io.reactivex.Single
import javax.inject.Inject

class SharedPrefsRoundsConfigurationRepository @Inject constructor(context: Context) : RoundsConfigurationRepository {

    private val sharedPreferences = context.getSharedPreferences("ROUNDS_CONFIG", Context.MODE_PRIVATE)

    override fun fetchRoundsConfig(): Single<RoundsConfiguration> =
            Single.just(readConfiguration())

    private fun saveConfiguration(roundsConfiguration: RoundsConfiguration) {
        sharedPreferences
                .edit()
                .putInt(SettingType.ROUNDS.name, roundsConfiguration.rounds)
                .putInt(SettingType.WORK_TIME.name, roundsConfiguration.workTime)
                .putInt(SettingType.REST_TIME.name, roundsConfiguration.restTime)
                .apply()
    }


    private fun readConfiguration(): RoundsConfiguration =
            RoundsConfiguration(
                    sharedPreferences.getInt(SettingType.ROUNDS.name, 8),
                    sharedPreferences.getInt(SettingType.WORK_TIME.name, 20),
                    sharedPreferences.getInt(SettingType.REST_TIME.name, 10)
            )

    override fun saveRoundsConfig(roundsConfiguration: RoundsConfiguration) {
        saveConfiguration(roundsConfiguration)
    }

}
