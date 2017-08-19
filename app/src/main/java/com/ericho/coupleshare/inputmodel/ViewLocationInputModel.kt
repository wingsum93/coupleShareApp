package com.ericho.coupleshare.inputmodel

import com.ericho.coupleshare.model.IncludeOption
import com.ericho.coupleshare.model.TimeOption
import com.google.gson.annotations.SerializedName

/**
 * Created by steve_000 on 10/8/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.inputmodel
 */

class ViewLocationInputModel : BaseAuthModel() {

  @SerializedName("timeOption")
  internal var timeOption: TimeOption = TimeOption.ALL
  @SerializedName("includeOption")
  internal var includeOption: IncludeOption = IncludeOption.ALL






  fun from(timeOption: TimeOption,includeOption: IncludeOption):ViewLocationInputModel{
    val z = ViewLocationInputModel()
    z.includeOption = includeOption
    z.timeOption = timeOption
    return z
  }

}
