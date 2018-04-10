package com.grzesiek.tabatatimer.persistence

import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class PersistenceModule {
    @Binds
    @Singleton
    abstract fun bindRepository(sharedPrefsRoundsConfigurationRepository: SharedPrefsRoundsConfigurationRepository): RoundsConfigurationRepository
}
