package com.grzesiek.tabatatimer.timer.model

import com.grzesiek.tabatatimer.persistence.RoundsConfiguration
import com.grzesiek.tabatatimer.timer.Round
import com.grzesiek.tabatatimer.timer.RoundType.*
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class RoundsBuilderTest {

    private val roundsBuilder = RoundsBuilder()

    @ParameterizedTest(name = "test {0}")
    @MethodSource("testSource")
    fun testRoundBuilder(roundsConfiguration: RoundsConfiguration, expected: List<Round>) {
        val rounds = roundsBuilder.build(roundsConfiguration)
        assertIterableEquals(expected, rounds)
    }

    companion object {
        @JvmStatic
        private fun testSource(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of(
                            RoundsConfiguration(0, 0, 0),
                            listOf(Round(PREPARATION, RoundsBuilder.PREPARATION_TIME),
                                    Round(COOL_DOWN, RoundsBuilder.COOL_DOWN_TIME))
                    ),
                    Arguments.of(
                            RoundsConfiguration(1, 20, 10),
                            listOf(Round(PREPARATION, RoundsBuilder.PREPARATION_TIME),
                                    Round(WORK, 20),
                                    Round(REST, 10),
                                    Round(COOL_DOWN, RoundsBuilder.COOL_DOWN_TIME))
                    ),
                    Arguments.of(
                            RoundsConfiguration(4, 20, 10),
                            listOf(Round(PREPARATION, RoundsBuilder.PREPARATION_TIME),
                                    Round(WORK, 20),
                                    Round(REST, 10),
                                    Round(WORK, 20),
                                    Round(REST, 10),
                                    Round(WORK, 20),
                                    Round(REST, 10),
                                    Round(WORK, 20),
                                    Round(REST, 10),
                                    Round(COOL_DOWN, RoundsBuilder.COOL_DOWN_TIME))
                    )
            )
        }
    }
}
