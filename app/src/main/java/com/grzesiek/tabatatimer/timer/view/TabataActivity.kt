package com.grzesiek.tabatatimer.timer.view

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.view.WindowManager
import com.grzesiek.tabatatimer.R
import com.grzesiek.tabatatimer.timer.Presenter
import com.grzesiek.tabatatimer.timer.RoundType
import com.grzesiek.tabatatimer.timer.View
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_timer.*
import javax.inject.Inject


class TabataActivity : WearableActivity(), View {
    @Inject
    lateinit var presenter: Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(R.layout.activity_timer)
        presenter.start()
    }


    override fun setRoundName(roundType: RoundType) {
        round_type.setText(when(roundType) {
            RoundType.PREPARATION -> R.string.preparation
            RoundType.WORK -> R.string.work
            RoundType.REST -> R.string.rest
            RoundType.COOL_DOWN -> R.string.cool_down
        })
    }

    override fun showTimeLeft(timeLeft: Int) {
        counter.text = timeLeft.toString()
    }

    override fun setBackgroundColor(roundType: RoundType) {
        root.setBackgroundColor(getColor(
                when (roundType) {
                    RoundType.PREPARATION -> R.color.grey
                    RoundType.WORK -> R.color.green
                    RoundType.REST -> R.color.red
                    RoundType.COOL_DOWN -> R.color.grey
                }
        ))
    }

    override fun showRoundsCount(roundCount: Int, totalRounds: Int) {
        rounds_count.text = getString(R.string.rounds_display, roundCount, totalRounds)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.stop()
    }

}
