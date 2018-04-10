package com.grzesiek.tabatatimer

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.wearable.activity.WearableActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.grzesiek.tabatatimer.settings.view.SettingsActivity
import com.grzesiek.tabatatimer.timer.view.TabataActivity

class MenuActivity : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = MenuAdapter(NavigationItem.values().toList()) {
            when(it) {
                NavigationItem.START -> startActivity(Intent(this, TabataActivity::class.java))
                NavigationItem.SETTINGS -> startActivity(Intent(this, SettingsActivity::class.java))
            }
        }
    }

    class MenuAdapter(private val items: List<NavigationItem>, private val listener: (item: NavigationItem) -> Unit) : PagerAdapter() {

        override fun getCount(): Int {
            return items.count()
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = LayoutInflater.from(container.context)
                    .inflate(R.layout.menu_item_view, container, false)
            container.addView(view)
            val iconView = view.findViewById<ImageView>(R.id.item_icon)
            val textView = view.findViewById<TextView>(R.id.item_text)
            iconView.setImageResource(items[position].icon)
            textView.text = items[position].text
            view.setOnClickListener { listener.invoke(items[position]) }
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }
    }

}

enum class NavigationItem(val text: String, val icon: Int) {
    START("Start", R.drawable.ic_play_arrow),
    SETTINGS("Settings", R.drawable.ic_settings)
}
