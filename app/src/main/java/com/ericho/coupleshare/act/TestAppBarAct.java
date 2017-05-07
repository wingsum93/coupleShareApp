package com.ericho.coupleshare.act;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ericho.coupleshare.R;

/**
 * Created by steve_000 on 7/5/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.act
 */

public class TestAppBarAct extends RxLifecycleAct {
    public static final String FLAG = "TestAppBarAct";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test_app_bar);
    }
}
