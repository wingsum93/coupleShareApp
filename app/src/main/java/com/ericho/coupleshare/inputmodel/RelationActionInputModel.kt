package com.ericho.coupleshare.inputmodel

import com.google.gson.annotations.SerializedName

/**
 * Created by steve_000 on 10/8/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.inputmodel
 */
class RelationActionInputModel :BaseAuthModel(){

  @SerializedName("action")
  var action:Action? = null
  @SerializedName("username")
  var username:String? = null



  enum class Action{
    ADD,
    APPROVE,
    DELETE

  }
}