package com.ericho.coupleshare.ui

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import com.ericho.coupleshare.R

/**
 * Created by steve_000 on 24/8/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.ui
 */
class SettingFrag : PreferenceFragmentCompat() {

  override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
    setPreferencesFromResource(R.xml.setting,rootKey)
  }

  companion object {
    @JvmStatic
   fun newInstance():SettingFrag{
      val f = SettingFrag()
      val b = Bundle()
      f.arguments = b
      return f
    }
  }
}