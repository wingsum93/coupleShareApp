package com.ericho.coupleshare.frag;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ericho.coupleshare.R;
import com.ericho.coupleshare.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by EricH on 10/5/2017.
 */

public class DummyFrag extends BaseFrag{
    public static final String K_COLOR = "K_COLOR";
    @BindView(R2.id.text1)
    TextView textView;
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R2.id.base)
    LinearLayout linearLayout;



    public static Fragment newInstance(@ColorRes int  color){
        DummyFrag frag = new DummyFrag();
        Bundle bundle = new Bundle();
        bundle.putInt(K_COLOR,color);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_dummy,container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        Bundle bundle = getArguments();
        int colorint = bundle.getInt(K_COLOR, android.R.color.holo_green_dark);
        textView.setBackgroundColor(colorint);
        linearLayout.setBackgroundColor(colorint);

        textView.setText("asd211dsdsdasdasdas21d3a1d3");
    }
}
