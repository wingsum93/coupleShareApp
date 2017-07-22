package com.ericho.coupleshare.act

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ProgressBar
import butterknife.bindView

import com.ericho.coupleshare.R
import com.ericho.coupleshare.util.IntentConstant

class LoadingAct : AppCompatActivity() {

    val progressBar :ProgressBar by bindView(R.id.progressBar)

    val handler:Handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        init()



    }

    fun init(){
        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        handler.postDelayed({
            val i = Intent(this,MainActivity3::class.java)
            startActivity(i)
            this.finish()

        },3000)
    }
}
