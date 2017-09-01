package com.ericho.coupleshare.ui

import android.os.Bundle
import com.ericho.coupleshare.frag.BaseFrag

/**
 * Created by steve_000 on 25/8/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.ui
 */
class LicenseFrag :BaseFrag() {



  companion object {
    @JvmStatic
    fun newInstance():LicenseFrag{
      val z = LicenseFrag()
      val b = Bundle()
      z.arguments = b
      return z
    }
  }
}