package com.ericho.coupleshare.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.widget.Adapter;

import com.ericho.coupleshare.R;
import com.ericho.coupleshare.R2;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by steve_000 on 28/4/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.act
 */

public class TestSelectPhotoAct extends RxLifecycleAct {

    public static final String hint = "TestSelectPhotoAct";
    public static final int PICK_IMAGE = 0xc111;


    @BindView(R2.id.recyclerView)
    protected RecyclerView recyclerView;
    @BindView(R2.id.fab)
    protected FloatingActionButton fab;
    private List<?> items;
    private Adapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test_select_photo);
        ButterKnife.bind(this);
        init();
    }

    private void init() {


        //on click
        fab.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){
            case PICK_IMAGE:

                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
