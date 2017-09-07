package com.ericho.coupleshare.act

import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import com.ericho.coupleshare.R
import com.ericho.coupleshare.frag.PhotoFrag
import com.ericho.coupleshare.frag.StatusFrag

/**
 * Created by steve_000 on 27/7/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.act
 */
class TestHoldAct:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.act_test_hold)
        supportFragmentManager
                .beginTransaction()
                .add(R.id.frag, PhotoFrag.newInstance())
                .commit()
        val fab :FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.frag, StatusFrag.newInstance())
                    .addToBackStack("")
                    .commit()
        }
    }


    override fun onBackPressed() {
        if (supportFragmentManager.popBackStackImmediate().not()) {
            super.onBackPressed()
        }
    }
}