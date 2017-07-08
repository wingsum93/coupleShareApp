package com.ericho.coupleshare.act

import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.ericho.coupleshare.R
import com.ericho.coupleshare.adapter.MyFragPageAdapter
import kotlinx.android.synthetic.main.act_test_collapsing_toolbar_tablayout.*

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.act
 */
class TestCollapseToolbarLayoutAndTabLayoutAct:RxLifecycleAct() {


    @BindView(R.id.toolbar_layout)
    internal var collapsingToolbarLayout: CollapsingToolbarLayout? = toolbar_layout



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