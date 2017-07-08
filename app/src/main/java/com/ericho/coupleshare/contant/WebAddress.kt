package com.ericho.coupleshare.contant

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.contant
 */
object WebAddress {

    @JvmField
    val API_REGISTER = "api/register"
    @JvmField
    val API_LOGIN = "api/login"
    @JvmField
    val API_CHANGE_PASSWORD = "api/changePassword"


    //operation
    @JvmField
    val API_PHOTO_UPLOAD = "api/uploadPhoto"
    val API_PHOTO_VIEW = "api/getPhotoList"
    val API_RELATION_CREATE = "api/createRelation"
    val API_RELATION_VIEW = "api/viewRelation"
    val API_RELATION_DEL = "api/deleteRelation"
    //
    val API_STATUS_CREATE = "api/deleteStatus"
    val API_STATUS_VIEW = "api/viewStatus"
    val API_STATUS_DEL = "api/createStatus"
    //LOC
    val API_LOC_GET = "api/getLocationList"
    val API_LOC_UPLOAD = "api/uploadLocation"

}