package com.ericho.coupleshare.mvp.data.local

import org.xutils.DbManager

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp.data.local
 */
object DbConfig {

    internal val DB_VERSION = 1

    @JvmStatic
    fun getDefaultConfig(): DbManager.DaoConfig {
        return DbManager.DaoConfig()
                .setDbName("local.db")//创建数据库的名称
                .setAllowTransaction(false)
                .setDbVersion(DB_VERSION)//数据库版本号
                .setDbUpgradeListener(MyDbUpgradeListener())//数据库更新操作
    }
}