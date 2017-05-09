package com.ericho.coupleshare.act;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ericho.coupleshare.R;

/**
 * Created by steve_000 on 9/5/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.act
 */

public class TestCollapseToolbarLayoutAndTabLayoutAct extends RxLifecycleAct {

    public static final String FLAG = "TestCollapseToolbarLayoutAndTabLayoutAct";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test_collapsing_toolbar_tablayout);
    }
}
