package com.grzesiek.tabatatimer.utils

import io.reactivex.Scheduler

interface SchedulersProvider {
    fun ui(): Scheduler
    fun computation(): Scheduler
    fun io(): Scheduler
}
