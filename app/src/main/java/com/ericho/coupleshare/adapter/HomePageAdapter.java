package com.ericho.coupleshare.adapter;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ericho.coupleshare.R;
import com.ericho.coupleshare.frag.DummyFrag;
import com.ericho.coupleshare.frag.LocationShowFrag;
import com.ericho.coupleshare.frag.PhotoFrag;
import com.ericho.coupleshare.frag.StatusFrag;

/**
 * Created by steve_000 on 21/6/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.adapter
 */

public class HomePageAdapter extends FragmentPagerAdapter {
    private Context context;

    public HomePageAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return PhotoFrag.newInstance();
            case 1:
                return StatusFrag.newInstance();
            case 2:
                return LocationShowFrag.newInstance();

            default:
                return LocationShowFrag.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return getString(R.string.photo);
            case 1:
                return getString(R.string.status);
            case 2:
                return getString(R.string.location);
            default:
                return "*";
        }
    }

    private String getString(@StringRes int resInt) {
        return context.getString(resInt);
    }
}
