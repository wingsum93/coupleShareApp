package com.ericho.coupleshare.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.ericho.coupleshare.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by steve_000 on 7/5/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.act
 */

public class TestAppBarAct extends RxLifecycleAct {
    public static final String FLAG = "TestAppBarAct";

    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.text)
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test_app_bar);
        ButterKnife.bind(this);
        textView.setText(generateLargeText());
    }

    private String generateLargeText() {
        int size = 35555;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append("a");
            if(i%13==0)
                builder.append("\n");

        }
        return builder.toString();
    }
}
