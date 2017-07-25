package com.ericho.coupleshare.act

import android.content.Intent
import android.graphics.Color
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
import com.ldoublem.loadingviewlib.view.LVGhost

class LoadingAct : AppCompatActivity() {

//    val progressBar :ProgressBar by bindView(R.id.progressBar)
    val mLvGhost :LVGhost by lazy { findViewById(R.id.lvGhost) as LVGhost }
    val handler:Handler = Handler(Looper.getMainLooper())

    val mLoadingTimeMill :Long = 3 * 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        init()



    }

    fun init(){

        handler.postDelayed({
            val i = Intent(this,MainActivity3::class.java)
            startActivity(i)
            this.finish()

        },mLoadingTimeMill)

        mLvGhost.setHandColor(Color.BLACK)
        mLvGhost.setViewColor(Color.WHITE)
        mLvGhost.startAnim()
    }
}
