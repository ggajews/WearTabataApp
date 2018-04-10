package com.grzesiek.tabatatimer.timer.model

import com.grzesiek.tabatatimer.timer.Round
import com.grzesiek.tabatatimer.timer.RoundState
import com.grzesiek.tabatatimer.timer.RoundType
import io.reactivex.schedulers.TestScheduler
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit

internal class RoundsExecutorTest {

    private val testScheduler = TestScheduler()
    private val roundsExecutor = RoundsExecutor(TestSchedulersProvider(testScheduler))

    @Test
    fun testSingleRoundExecution() {
        val rounds = listOf(Round(RoundType.WORK, 2))

        val testObserver = roundsExecutor
                .start(rounds)
                .test()

        testScheduler.triggerActions()
        testObserver.assertValue(RoundState(Round(RoundType.WORK, 2), 2))

        testScheduler.advanceTimeBy(1L, TimeUnit.SECONDS)
        testObserver.assertValueAt(1, RoundState(Round(RoundType.WORK, 2), 1))

        testScheduler.advanceTimeBy(1L, TimeUnit.SECONDS)
        testObserver.assertComplete()

    }

    @Test
    fun testEmptyRoundsExecution() {
        val emptyRounds = listOf<Round>()

        val testObserver = roundsExecutor
                .start(emptyRounds)
                .test()

        testScheduler.triggerActions()

        testObserver.assertNoValues()
        testObserver.assertComplete()
    }

    @Test
    fun testDelayBetweenRounds() {
        val rounds = listOf(Round(RoundType.WORK, 2), Round(RoundType.REST, 1))

        val testObserver = roundsExecutor
                .start(rounds)
                .test()

        testScheduler.triggerActions()
        testObserver.assertValueCount(1)

        testScheduler.advanceTimeBy(1L, TimeUnit.SECONDS)
        testObserver.assertValueCount(2)

        testScheduler.advanceTimeBy(1L, TimeUnit.SECONDS)
        testObserver.assertValueCount(3)
        testObserver.assertValueAt(2, RoundState(Round(RoundType.REST, 1), 1))

        testScheduler.advanceTimeBy(1L, TimeUnit.SECONDS)
        testObserver.assertComplete()
    }
}
