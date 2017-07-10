package com.ericho.coupleshare.adapter

import android.content.Context
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.ericho.coupleshare.R
import com.ericho.coupleshare.frag.*

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.adapter
 */
class HomePageAdapter constructor(fm:FragmentManager,context: Context):FragmentPagerAdapter(fm) {

    private var context:Context

    private val f1:BaseFrag = PhotoFrag.newInstance();
    private val f2:BaseFrag = StatusFrag.newInstance();
    private val f3:LocationShowFrag = LocationShowFrag.newInstance();
    init {
        this.context = context
    }

    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return f1
            1 -> return f2
            2 -> return f3

            else -> return LocationShowFrag.newInstance()
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        when (position) {
            0 -> return getString(R.string.photo)
            1 -> return getString(R.string.status)
            2 -> return getString(R.string.location)
            else -> return "*"
        }
    }

    private fun getString(@StringRes resInt: Int): String {
        return context.getString(resInt)
    }

}