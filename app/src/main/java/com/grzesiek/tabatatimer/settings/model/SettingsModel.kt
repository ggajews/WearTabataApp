package com.grzesiek.tabatatimer.settings.model

import com.grzesiek.tabatatimer.persistence.RoundsConfiguration
import com.grzesiek.tabatatimer.persistence.RoundsConfigurationRepository
import com.grzesiek.tabatatimer.settings.Action
import com.grzesiek.tabatatimer.settings.Model
import com.grzesiek.tabatatimer.settings.SettingType
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class SettingsModel @Inject constructor(private val repository: RoundsConfigurationRepository) : Model {

    private val currentConfig = BehaviorSubject.create<RoundsConfiguration>()
    private var disposable: Disposable

    init {
        disposable = repository
                .fetchRoundsConfig()
                .subscribe(currentConfig::onNext)
    }

    override fun getRoundsConfiguration(): Observable<RoundsConfiguration> =
            currentConfig
                    .doOnDispose { disposable.dispose() }
                    .distinctUntilChanged()

    override fun dispatchAction(action: Action) {
        val oldConfig = currentConfig.value
        val newConfig = when (action.settingType) {
            SettingType.ROUNDS -> oldConfig.copy(rounds = applyAction(oldConfig.rounds, action))
            SettingType.WORK_TIME -> oldConfig.copy(workTime = applyAction(oldConfig.workTime, action))
            SettingType.REST_TIME -> oldConfig.copy(restTime = applyAction(oldConfig.restTime, action))
        }
        currentConfig.onNext(newConfig)
        repository.saveRoundsConfig(newConfig)
    }

    private fun applyAction(value: Int, action: Action): Int {
        val settingType = action.settingType
        return when (action) {
            is Action.Increment -> minOf(value + settingType.step, settingType.maxValue)
            is Action.Decrement -> maxOf(value - settingType.step, settingType.minValue)
        }
    }
}
