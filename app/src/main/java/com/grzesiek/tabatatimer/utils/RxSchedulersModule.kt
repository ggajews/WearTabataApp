package com.grzesiek.tabatatimer.utils

import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RxSchedulersModule {
    @Binds
    @Singleton
    abstract fun bindSchedulersProvider(appSchedulersProvider: AppSchedulersProvider): SchedulersProvider
}
