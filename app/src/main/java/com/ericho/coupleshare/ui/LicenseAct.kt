package com.ericho.coupleshare.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ericho.coupleshare.R
import com.ericho.coupleshare.util.action

/**
 * Created by steve_000 on 25/8/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.ui
 */
class LicenseAct :AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.act_frag)
    initView()
  }

  private fun initView() {
    supportFragmentManager.action {
      add(R.id.frameLayout,LicenseFrag.newInstance())
    }
  }

}