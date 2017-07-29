package com.ericho.coupleshare.db

import com.ericho.coupleshare.App
import com.ericho.coupleshare.model.LocationTo
import com.ericho.coupleshare.mvp.data.local.DbConfig
import com.ericho.coupleshare.util.getUserName
import com.ericho.coupleshare.util.safe
import org.xutils.DbManager
import org.xutils.db.sqlite.WhereBuilder
import org.xutils.ex.DbException
import org.xutils.x
import timber.log.Timber

/**
 * Created by steve_000 on 14/7/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.db
 */
class Dao :CoupleShareDao{

    private var daoConfig :DbManager.DaoConfig = DbConfig.getDefaultConfig()
    private var dbManager :DbManager = x.getDb(daoConfig)

    companion object {
        private var INSTANCE :Dao? = null
        @JvmStatic
        fun getInstance() :Dao{
            if (INSTANCE==null){
                INSTANCE = Dao()
            }
            return INSTANCE as Dao
        }
    }


    override fun saveLocationTO(locations: List<LocationTo>) {
        try {
            dbManager.saveOrUpdate(locations)

        }catch (e:DbException){
            Timber.w(e)
        }
    }

    override fun saveLocationTO(locations: LocationTo) {
        try {
            dbManager.saveOrUpdate(locations)
        }catch (e:DbException){
            Timber.w(e)
        }
    }

    override fun delLocationTO(locations: LocationTo) {
        try {
            dbManager.delete(locations)
        }catch (e:DbException){
            Timber.w(e)
        }
    }

    override fun getLocationToList(): List<LocationTo> {
        try {
            val username = App.context!!.getUserName()
            val whereBuilder:WhereBuilder = WhereBuilder.b("username","=",username)
            val list = dbManager.selector(LocationTo::class.java)
                    .where(whereBuilder)
                    .orderBy("date",true).findAll()
            return list.safe()
        }catch (e:DbException){
            Timber.w(e)
            return ArrayList<LocationTo>()
        }
    }
}