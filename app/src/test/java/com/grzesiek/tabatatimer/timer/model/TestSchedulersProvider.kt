package com.grzesiek.tabatatimer.timer.model

import com.grzesiek.tabatatimer.utils.SchedulersProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler

class TestSchedulersProvider(val testScheduler: Scheduler) : SchedulersProvider {
    override fun ui(): Scheduler = testScheduler
    override fun computation(): Scheduler = testScheduler
    override fun io(): Scheduler = testScheduler
}
