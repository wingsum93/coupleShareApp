package com.ericho.coupleshare.mvp.data.local

import org.xutils.DbManager

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp.data.local
 */
class MyDbUpgradeListener: DbManager.DbUpgradeListener{
    override fun onUpgrade(db: DbManager?, oldVersion: Int, newVersion: Int) {

    }

}