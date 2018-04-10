package com.grzesiek.tabatatimer.settings

import com.grzesiek.tabatatimer.persistence.RoundsConfiguration
import io.reactivex.Observable

interface View {
    fun showSettings(settingsList: List<SettingsItem>)
    fun increments(): Observable<SettingType>
    fun decrements(): Observable<SettingType>
}

interface Presenter {
    fun start()
    fun stop()
}

interface Model {
    fun getRoundsConfiguration(): Observable<RoundsConfiguration>
    fun dispatchAction(action: Action)
}

sealed class Action(val settingType: SettingType) {
    class Increment(settingType: SettingType) : Action(settingType)
    class Decrement(settingType: SettingType) : Action(settingType)
}

data class SettingsItem(val settingType: SettingType, val value: Int)

enum class SettingType(val minValue: Int, val maxValue: Int, val step: Int) {
    ROUNDS(1, 99, 1),
    WORK_TIME(5, 300, 5),
    REST_TIME(5, 300, 5)
}
