package com.ericho.coupleshare.act

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.ericho.coupleshare.R

class ViewPhotoAct : AppCompatActivity() {

    var img:ImageView? = null
    var btn_left:FloatingActionButton? = null
    var btn_right:FloatingActionButton? = null

    var items:Array<String>? = null
    var position:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.act_view_photo)
        init();

    }

    private fun init() {

        img = findViewById(R.id.imageView) as ImageView
        btn_left = findViewById(R.id.left) as FloatingActionButton
        btn_right = findViewById(R.id.right) as FloatingActionButton

        val i = intent

        items = i!!.getStringArrayExtra(ggg)
        position = i.getIntExtra(ggg2,0)

        loadImage(position)

        btn_left?.setOnClickListener ({
            if(position!=0) loadImage(--position)
        })
        btn_right?.setOnClickListener ({
            if(position!=items!!.lastIndex) loadImage(++position)
        })
    }

    fun loadImage(pos:Int){

        Glide.with(this)
                .load(items!![pos])
                .into(img)
        val i=Intent()
        i.putExtra("RESULT",pos)
        setResult(Activity.RESULT_OK,i)
    }

    override fun onDestroy() {

        super.onDestroy()
    }

    companion object {
        val ggg = "ggg"
        val ggg2 = "ggg2"

        fun newIntent(context: Context,uris:Array<String>,currentPosition:Int):Intent{
            val i = Intent(context,ViewPhotoAct::class.java)
            i.putExtra(ggg,uris)
            i.putExtra(ggg2,currentPosition)
            return i
        }
    }



}
