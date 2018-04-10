package com.grzesiek.tabatatimer

import android.app.Application
import android.content.Context
import com.grzesiek.tabatatimer.persistence.PersistenceModule
import com.grzesiek.tabatatimer.utils.RxSchedulersModule
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class,
        AndroidInjectionModule::class,
        ActivityBindingModule::class,
        PersistenceModule::class,
        RxSchedulersModule::class,
        SystemServiceModule::class))
interface AppComponent {
    fun inject(tabataTimerApplication: TabataTimerApplication)
}

@Module
class AppModule(private val application: Application) {
    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application
}
