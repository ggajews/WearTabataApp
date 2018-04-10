package com.grzesiek.tabatatimer.timer.presenter

import com.grzesiek.tabatatimer.timer.ActivityController
import com.grzesiek.tabatatimer.timer.view.TabataActivity
import javax.inject.Inject

class TabataActivityController @Inject constructor(
        private val tabataActivity: TabataActivity) : ActivityController {

    override fun finish() {
        tabataActivity.finish()
    }
}
