package com.grzesiek.tabatatimer.settings.model

import com.grzesiek.tabatatimer.persistence.RoundsConfiguration
import com.grzesiek.tabatatimer.persistence.RoundsConfigurationRepository
import com.grzesiek.tabatatimer.settings.Action
import com.grzesiek.tabatatimer.settings.SettingType.*
import io.reactivex.subjects.SingleSubject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.stream.Stream

internal class SettingsModelTest {

    @Mock
    private lateinit var repository: RoundsConfigurationRepository
    private lateinit var settingsModel: SettingsModel
    private val configObservable = SingleSubject.create<RoundsConfiguration>()

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(repository.fetchRoundsConfig()).thenReturn(configObservable)
        settingsModel = SettingsModel(repository)
    }

    @ParameterizedTest
    @MethodSource("regularActions")
    fun testAction(action: Action, expected: RoundsConfiguration) {
        configObservable.onSuccess(RoundsConfiguration(8, 20, 10))
        settingsModel.dispatchAction(action)
        Mockito.verify(repository).saveRoundsConfig(expected)
        settingsModel.getRoundsConfiguration().test().assertValue(expected)
    }


    @ParameterizedTest
    @MethodSource("minMaxActions")
    fun testMinMaxValue(action: Action, roundsConfiguration: RoundsConfiguration) {
        configObservable.onSuccess(roundsConfiguration)
        settingsModel.dispatchAction(action)
        Mockito.verify(repository).saveRoundsConfig(roundsConfiguration)
        settingsModel.getRoundsConfiguration().test().assertValue(roundsConfiguration)
    }

    companion object {
        @JvmStatic
        private fun regularActions(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of(
                            Action.Increment(ROUNDS) as Action,
                            RoundsConfiguration(9, 20, 10)
                    ),
                    Arguments.of(
                            Action.Increment(WORK_TIME),
                            RoundsConfiguration(8, 25, 10)
                    ),
                    Arguments.of(
                            Action.Increment(REST_TIME),
                            RoundsConfiguration(8, 20, 15)
                    ),
                    Arguments.of(
                            Action.Decrement(ROUNDS),
                            RoundsConfiguration(7, 20, 10)
                    ),
                    Arguments.of(
                            Action.Decrement(WORK_TIME),
                            RoundsConfiguration(8, 15, 10)
                    ),
                    Arguments.of(
                            Action.Decrement(REST_TIME),
                            RoundsConfiguration(8, 20, 5)
                    )
            )
        }

        @JvmStatic
        private fun minMaxActions(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of(
                            Action.Increment(ROUNDS) as Action,
                            RoundsConfiguration(ROUNDS.maxValue, 20, 10)
                    ),
                    Arguments.of(
                            Action.Decrement(ROUNDS),
                            RoundsConfiguration(ROUNDS.minValue, 20, 10)
                    ),
                    Arguments.of(
                            Action.Increment(WORK_TIME),
                            RoundsConfiguration(8, WORK_TIME.maxValue, 10)
                    ),
                    Arguments.of(
                            Action.Decrement(WORK_TIME),
                            RoundsConfiguration(8, WORK_TIME.minValue, 15)
                    ),
                    Arguments.of(
                            Action.Increment(REST_TIME),
                            RoundsConfiguration(8, 20, REST_TIME.maxValue)
                    ),
                    Arguments.of(
                            Action.Decrement(REST_TIME),
                            RoundsConfiguration(8, 20, REST_TIME.minValue)
                    )
            )
        }
    }
}
