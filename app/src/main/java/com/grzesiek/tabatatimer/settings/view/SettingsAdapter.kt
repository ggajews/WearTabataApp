package com.grzesiek.tabatatimer.settings.view

import android.support.annotation.StringRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.grzesiek.tabatatimer.R
import com.grzesiek.tabatatimer.settings.SettingType
import com.grzesiek.tabatatimer.settings.SettingType.*
import com.grzesiek.tabatatimer.settings.SettingsItem
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.settings_item.view.*
import kotlin.properties.Delegates

class SettingsAdapter : RecyclerView.Adapter<SettingsViewHolder>() {
    var settings: List<SettingsItem> by Delegates.observable(listOf()) { _, _, _ ->
        notifyDataSetChanged()
    }

    val decrementsSubject: PublishSubject<SettingType> = PublishSubject.create<SettingType>()
    val incrementsSubject: PublishSubject<SettingType> = PublishSubject.create<SettingType>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        val itemView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.settings_item, parent, false)
        return SettingsViewHolder(itemView)
    }

    override fun getItemCount(): Int = settings.size

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        holder.bind(settings[position], decrementsSubject, incrementsSubject)
    }

}

class SettingsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val titleView: TextView = itemView.title
    private val valueView: TextView = itemView.value
    private val incrementButton = itemView.increment
    private val decrementButton = itemView.decrement

    fun bind(settingsItem: SettingsItem, decrementsSubject: PublishSubject<SettingType>, incrementsSubject: PublishSubject<SettingType>) {
        titleView.setText(getTitle(settingsItem.settingType))
        valueView.text = settingsItem.value.toString()
        decrementButton.setOnClickListener { decrementsSubject.onNext(settingsItem.settingType) }
        incrementButton.setOnClickListener { incrementsSubject.onNext(settingsItem.settingType) }
    }

    @StringRes
    private fun getTitle(settingType: SettingType): Int = when(settingType) {
        ROUNDS -> R.string.settings_rounds_title
        WORK_TIME -> R.string.settings_work_time_title
        REST_TIME -> R.string.settings_rest_time_title
    }
}
