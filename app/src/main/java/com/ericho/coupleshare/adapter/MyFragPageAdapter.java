package com.ericho.coupleshare.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ericho.coupleshare.frag.DummyFrag;

/**
 * Created by EricH on 10/5/2017.
 */

public class MyFragPageAdapter extends FragmentPagerAdapter {

    public MyFragPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return DummyFrag.newInstance(android.R.color.holo_blue_dark);
            case 2:
                return DummyFrag.newInstance(android.R.color.tertiary_text_dark);
            case 1:
                return DummyFrag.newInstance(android.R.color.widget_edittext_dark);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Blue";
            case 1:
                return "Dark";
            case 2:
                return "Grey";
            default:
                return super.getPageTitle(position);
        }
    }
}
