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
import com.ericho.coupleshare.mvp.Photo;
import com.ericho.coupleshare.mvp.PhotosContract;
import com.ericho.coupleshare.mvp.presenter.PhotoPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by steve_000 on 22/6/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.frag
 */

public class PhotoFrag extends BaseFrag implements PhotosContract.View{

    @BindView(R.id.recyclerView)
    protected RecyclerView recyclerView;
    @BindView(R.id.fab)
    protected FloatingActionButton floatingActionButton;

    private PhotoAdapter adapter;

    private PhotosContract.Presenter presenter;

    private List<Photo> list;

    public static PhotoFrag newInstance(){
        PhotoFrag frag = new PhotoFrag();
        Bundle bundle = new Bundle();
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
        View view = inflater.inflate(R.layout.frag_photo,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    private void init() {


        //listener
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recyclerView.setAdapter(adapter);
        floatingActionButton.setOnClickListener(v -> Timber.d("fab click"));

        list = new ArrayList<>();
        adapter = new PhotoAdapter(getActivity(),list);

        presenter = new PhotoPresenter(this);
        presenter.start();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

    }


    @Override
    public void setPresenter(PhotosContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showAddPhotoUI() {

    }

    @Override
    public void showPhotoList(List<Photo> photos) {
        adapter.update(photos);
    }
}
