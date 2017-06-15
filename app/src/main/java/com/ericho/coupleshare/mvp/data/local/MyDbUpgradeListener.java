package com.ericho.coupleshare.mvp.data.local;

import org.xutils.DbManager;

/**
 * Created by steve_000 on 12/6/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp.data.local
 */

public class MyDbUpgradeListener implements DbManager.DbUpgradeListener {
    @Override
    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

    }
}
