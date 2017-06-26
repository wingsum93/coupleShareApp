package com.ericho.coupleshare.mvp;

import java.util.List;

/**
 * Created by steve_000 on 17年6月25日.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp
 */

public interface PhotosContract {
    interface View extends BaseView<Presenter>{
        void showAddPhotoUI();

        void showPhotoList(List<Photo> photos);

    }
    interface Presenter extends BasePresenter{
        void loadPhotos();

        void addNewPhotos();
    }
}
