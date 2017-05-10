package com.ericho.coupleshare.frag;

import android.support.v4.app.Fragment;

import butterknife.Unbinder;

/**
 * Created by EricH on 10/5/2017.
 */

public abstract class BaseFrag extends Fragment {
    Unbinder unbinder;


    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroyView();
    }
}
