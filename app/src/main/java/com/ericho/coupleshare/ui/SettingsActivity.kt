package com.ericho.coupleshare.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.ericho.coupleshare.R
import com.ericho.coupleshare.frag.DummyFrag
import com.ericho.coupleshare.util.action

/**
 * Created by steve_000 on 24/8/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.ui
 */
class SettingsActivity:AppCompatActivity() {

  private val fragId = R.id.frameLayout

  var frag :Fragment? = null


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.act_settings)

    initView()
  }

  private fun initView() {
    supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)


    supportFragmentManager.action {
      add(fragId, SettingFrag.newInstance())
    }
//    supportFragmentManager.beginTransaction()
//            .add(R.id.frameLayout,DummyFrag.newInstance())
//            .commit()
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when(item!!.itemId){
      android.R.id.home-> {
        finish()
        return true
      }
      else -> return super.onOptionsItemSelected(item)
    }
  }

  override fun onSaveInstanceState(outState: Bundle?) {

    super.onSaveInstanceState(outState)
  }
}