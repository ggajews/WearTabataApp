package com.grzesiek.tabatatimer.settings.view

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import com.grzesiek.tabatatimer.R
import com.grzesiek.tabatatimer.settings.Presenter
import com.grzesiek.tabatatimer.settings.SettingType
import com.grzesiek.tabatatimer.settings.SettingsItem
import com.grzesiek.tabatatimer.settings.View
import dagger.android.AndroidInjection
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_settings.*
import javax.inject.Inject

class SettingsActivity : Activity(), View {
    @Inject
    lateinit var presenter: Presenter
    private val adapter = SettingsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_settings)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter
        recycler_view.isEdgeItemsCenteringEnabled = true
        PagerSnapHelper().attachToRecyclerView(recycler_view)
        presenter.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.stop()
    }

    override fun showSettings(settingsList: List<SettingsItem>) {
        adapter.settings = settingsList
    }

    override fun increments(): Observable<SettingType> = adapter.incrementsSubject

    override fun decrements(): Observable<SettingType> = adapter.decrementsSubject
}
