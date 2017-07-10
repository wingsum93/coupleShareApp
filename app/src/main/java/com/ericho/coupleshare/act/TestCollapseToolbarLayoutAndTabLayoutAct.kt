package com.ericho.coupleshare.act

import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.FragmentPagerAdapter
import butterknife.bindView
import com.ericho.coupleshare.R
import com.ericho.coupleshare.adapter.MyFragPageAdapter
import kotlinx.android.synthetic.main.act_test_collapsing_toolbar_tablayout.*

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.act
 */
class TestCollapseToolbarLayoutAndTabLayoutAct:RxLifecycleAct() {


    val collapsingToolbarLayout: CollapsingToolbarLayout by bindView(R.id.toolbar_layout)



    internal var fragmentPagerAdapter: FragmentPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_test_collapsing_toolbar_tablayout)
        init()
    }

    private fun init() {
        fragmentPagerAdapter = MyFragPageAdapter(supportFragmentManager)
        viewPager.setAdapter(fragmentPagerAdapter)
        tabLayout.setupWithViewPager(viewPager)
    }
    companion object {
        @JvmField
        val FLAG = "TestCollapseToolbarLayoutAndTabLayoutAct"
    }
}