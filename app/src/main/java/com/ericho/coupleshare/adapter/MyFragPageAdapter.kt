package com.ericho.coupleshare.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.ericho.coupleshare.frag.DummyFrag

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.adapter
 */
class MyFragPageAdapter constructor(fm:FragmentManager):FragmentPagerAdapter(fm) {


    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return DummyFrag.newInstance(android.R.color.holo_blue_dark)
            2 -> return DummyFrag.newInstance(android.R.color.tertiary_text_dark)
            1 -> return DummyFrag.newInstance(android.R.color.widget_edittext_dark)
        }
        return null
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        when (position) {
            0 -> return "Blue"
            1 -> return "Dark"
            2 -> return "Grey"
            else -> return super.getPageTitle(position)
        }
    }

}