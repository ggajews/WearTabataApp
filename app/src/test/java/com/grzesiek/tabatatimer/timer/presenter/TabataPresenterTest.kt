package com.grzesiek.tabatatimer.timer.presenter

import com.grzesiek.tabatatimer.timer.ActivityController
import com.grzesiek.tabatatimer.timer.Model
import com.grzesiek.tabatatimer.timer.RoundType.PREPARATION
import com.grzesiek.tabatatimer.timer.RoundType.WORK
import com.grzesiek.tabatatimer.timer.TabataState
import com.grzesiek.tabatatimer.timer.View
import com.grzesiek.tabatatimer.timer.model.TestSchedulersProvider
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

internal class TabataPresenterTest {

    @Mock
    private lateinit var view: View

    @Mock
    private lateinit var model: Model

    @Mock
    private lateinit var vibrationController: VibrationController

    @Mock
    private lateinit var activityController: ActivityController

    private val testSchedulersProvider = TestSchedulersProvider(Schedulers.trampoline())

    private lateinit var presenter: TabataPresenter

    private lateinit var tabataObservable: PublishSubject<TabataState>

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = TabataPresenter(view, model, vibrationController, activityController, testSchedulersProvider)

        tabataObservable = PublishSubject.create<TabataState>()
        `when`(model.runTabata()).thenReturn(tabataObservable)
    }

    @Test
    fun testTimeUpdates() {
        presenter.start()

        tabataObservable.onNext(TabataState(PREPARATION, 10, 0, 8))
        verify(view).showTimeLeft(10)

        tabataObservable.onNext(TabataState(PREPARATION, 9, 0, 8))
        verify(view).showTimeLeft(9)
    }

    @Test
    fun testFinishAfterCounterFinishes() {
        presenter.start()

        tabataObservable.onComplete()
        verify(activityController).finish()
    }

    @Test
    fun testRoundsChanges() {
        presenter.start()

        tabataObservable.onNext(TabataState(PREPARATION, 10 , 0 ,8))
        verify(vibrationController).vibrate()
        verify(view).setBackgroundColor(PREPARATION)
        verify(view).setRoundName(PREPARATION)
        reset(vibrationController, view)

        tabataObservable.onNext(TabataState(PREPARATION, 9 , 0 ,8))
        verify(vibrationController, never()).vibrate()
        verify(view, never()).setBackgroundColor(PREPARATION)
        verify(view, never()).setRoundName(PREPARATION)

        tabataObservable.onNext(TabataState(WORK, 10 , 0 ,8))
        verify(vibrationController).vibrate()
        verify(view).setBackgroundColor(WORK)
        verify(view).setRoundName(WORK)
    }

    @Test
    fun testRoundsCounter() {
        presenter.start()
        tabataObservable.onNext(TabataState(PREPARATION, 10 , 0 ,8))
        verify(view).showRoundsCount(0, 8)
        reset(view)

        tabataObservable.onNext(TabataState(PREPARATION, 9 , 0 ,8))
        verify(view, never()).showRoundsCount(anyInt(), anyInt())

        tabataObservable.onNext(TabataState(WORK, 9 , 1 ,8))
        verify(view).showRoundsCount(1, 8)
    }
}
