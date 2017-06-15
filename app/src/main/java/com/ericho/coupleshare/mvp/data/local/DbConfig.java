package com.ericho.coupleshare.mvp.data.local;

import android.content.Context;

import org.xutils.DbManager;

/**
 * Created by steve_000 on 12/6/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp.data.local
 */

public class DbConfig {
    static final int DB_VERSION = 1;

    public static DbManager.DaoConfig getDefaultConfig(){
        return new DbManager.DaoConfig()
                .setDbName("local.db")//创建数据库的名称
                .setAllowTransaction(false)
                .setDbVersion(DB_VERSION)//数据库版本号
                .setDbUpgradeListener(new MyDbUpgradeListener());//数据库更新操作
    }
}
