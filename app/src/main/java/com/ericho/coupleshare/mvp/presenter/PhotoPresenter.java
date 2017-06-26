package com.ericho.coupleshare.mvp.presenter;

import com.ericho.coupleshare.R;
import com.ericho.coupleshare.mvp.Photo;
import com.ericho.coupleshare.mvp.PhotosContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by steve_000 on 17年6月25日.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp.presenter
 */

public class PhotoPresenter implements PhotosContract.Presenter {

    PhotosContract.View mView;


    public  PhotoPresenter(PhotosContract.View view){
        this.mView = view;
    }


    @Override
    public void start() {
        loadPhotos();
    }

    @Override
    public void loadPhotos() {
        mView.showPhotoList(dummyPhotoList());
    }

    @Override
    public void addNewPhotos() {

    }


    private List<Photo> dummyPhotoList(){
        List<Photo> res = new ArrayList<>();
        Photo p =new Photo();
        p.setImageInt(R.drawable.ic_menu_manage);
        res.add(p);
        Photo p2 =new Photo();
        p.setImageInt(R.drawable.ic_menu_camera);
        res.add(p2);
        return res;
    }
}
