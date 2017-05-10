package com.ericho.coupleshare.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.ericho.coupleshare.R;
import com.ericho.coupleshare.adapter.MyFragPageAdapter;
import com.ericho.coupleshare.frag.DummyFrag;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by steve_000 on 9/5/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.act
 */

public class TestCollapseToolbarLayoutAndTabLayoutAct extends RxLifecycleAct {
    public static final String FLAG = "TestCollapseToolbarLayoutAndTabLayoutAct";

    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    FragmentPagerAdapter fragmentPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test_collapsing_toolbar_tablayout);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        fragmentPagerAdapter = new MyFragPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
