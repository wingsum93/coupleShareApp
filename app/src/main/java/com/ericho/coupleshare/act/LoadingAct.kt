package com.ericho.coupleshare.act

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import com.ericho.coupleshare.R
import com.ldoublem.loadingviewlib.view.LVGhost

class LoadingAct : AppCompatActivity() {

//    val progressBar :ProgressBar by bindView(R.id.progressBar)
    var mLvGhost :LVGhost? = null
    val handler:Handler = Handler(Looper.getMainLooper())

    val mLoadingTimeMill :Long = 1 * 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_loading)

        init()



    }

    fun init(){

        handler.postDelayed({
            val i = Intent(this,MainActivity3::class.java)
            startActivity(i)
            this.finish()

        },mLoadingTimeMill)
        mLvGhost = findViewById(R.id.lvGhost) as LVGhost
        mLvGhost!!.setHandColor(Color.BLACK)
        mLvGhost!!.setViewColor(Color.WHITE)
        mLvGhost!!.startAnim()

    }

    override fun onDestroy() {
        mLvGhost?.destroyDrawingCache()
        mLvGhost?.stopAnim()
        super.onDestroy()
    }
}
