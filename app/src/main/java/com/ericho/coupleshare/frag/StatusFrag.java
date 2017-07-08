package com.ericho.coupleshare.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ericho.coupleshare.R;
import com.ericho.coupleshare.adapter.PhotoAdapter;
import com.ericho.coupleshare.interf.FabListener;
import com.ericho.coupleshare.mvp.StatusBo;
import com.ericho.coupleshare.mvp.StatusContract;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by steve_000 on 22/6/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.frag
 */

public class StatusFrag extends BaseFrag implements StatusContract.View,FabListener{

    @BindView(R.id.recyclerView)
    protected RecyclerView recyclerView;


    private PhotoAdapter adapter;

    private StatusContract.Presenter presenter;

    private List<StatusBo> list;

    public static StatusFrag newInstance(){
        StatusFrag frag = new StatusFrag();
        Bundle bundle = new Bundle();
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_status,container,false);
        ButterKnife.bind(this,view);
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

    }
    private void init() {


        //listener
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recyclerView.setAdapter(adapter);

        list = new ArrayList<>();
        adapter = new PhotoAdapter(getActivity(),list);

    }





    @Override
    public void setPresenter(StatusContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onAttachFloatingActionListener(FloatingActionButton floatingActionButton) {
        floatingActionButton.setVisibility(View.GONE);
    }
}
