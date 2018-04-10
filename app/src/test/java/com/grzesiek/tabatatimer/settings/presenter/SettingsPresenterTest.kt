package com.grzesiek.tabatatimer.settings.presenter

import com.grzesiek.tabatatimer.persistence.RoundsConfiguration
import com.grzesiek.tabatatimer.settings.*
import io.reactivex.subjects.PublishSubject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

internal class SettingsPresenterTest {

    @Mock
    private lateinit var view: View

    @Mock
    private lateinit var model: Model

    private lateinit var presenter: SettingsPresenter

    private lateinit var configurationObservable: PublishSubject<RoundsConfiguration>
    private lateinit var incrementsObservable: PublishSubject<SettingType>
    private lateinit var decrementsObservable: PublishSubject<SettingType>

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = SettingsPresenter(view, model)

        configurationObservable = PublishSubject.create()
        incrementsObservable = PublishSubject.create()
        decrementsObservable = PublishSubject.create()
        `when`(model.getRoundsConfiguration()).thenReturn(configurationObservable)
        `when`(view.decrements()).thenReturn(decrementsObservable)
        `when`(view.increments()).thenReturn(incrementsObservable)
    }

    @Test
    fun testSettingsDisplay() {
        presenter.start()

        configurationObservable.onNext(RoundsConfiguration(8, 20, 10))
        verify(view).showSettings(listOf(
                SettingsItem(SettingType.ROUNDS, 8),
                SettingsItem(SettingType.WORK_TIME, 20),
                SettingsItem(SettingType.REST_TIME, 10)
        ))
    }

    @ParameterizedTest(name = "Test {0} increments")
    @EnumSource(SettingType::class)
    fun testIncrements(settingType: SettingType) {
        presenter.start()
        incrementsObservable.onNext(settingType)
        val argumentCaptor = ArgumentCaptor.forClass(Action::class.java)

        verify(model).dispatchAction(capture(argumentCaptor) ?: Action.Increment(SettingType.WORK_TIME))
        assert(argumentCaptor.value is Action.Increment)
        assertEquals(settingType, argumentCaptor.value.settingType)
    }

    @ParameterizedTest(name = "Test {0} decrements")
    @EnumSource(SettingType::class)
    fun testDecrements(settingType: SettingType) {
        presenter.start()
        incrementsObservable.onNext(settingType)
        val argumentCaptor = ArgumentCaptor.forClass(Action::class.java)

        verify(model).dispatchAction(capture(argumentCaptor) ?: Action.Increment(SettingType.WORK_TIME))
        assert(argumentCaptor.value is Action.Decrement)
        assertEquals(settingType, argumentCaptor.value.settingType)
    }
}

fun <T> capture(argumentCaptor: ArgumentCaptor<T>): T = argumentCaptor.capture()

