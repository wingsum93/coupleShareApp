package com.ericho.coupleshare.network

import com.ericho.coupleshare.model.StatusTO
import com.ericho.coupleshare.mvp.data.local.DbConfig
import org.xutils.DbManager
import org.xutils.x
import timber.log.Timber
import java.io.IOException

/**
 * Created by steve_000 on 14/7/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.http
 */
class StatusNoticeManager :IStatusNoticeManager{

    var dbManager : DbManager

    init {
        dbManager = x.getDb(DbConfig.getDefaultConfig())
    }

    fun save(item: StatusTO) {
        try {
            dbManager.saveOrUpdate(item)

        }catch (e:IOException){
            Timber.w(e)
        }
    }

}


