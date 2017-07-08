package com.ericho.coupleshare.act

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView
import android.widget.Adapter
import butterknife.bindView
import com.ericho.coupleshare.R

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.act
 */
class TestSelectPhotoAct: RxLifecycleAct() {
    val recyclerView: RecyclerView by bindView<RecyclerView>(R.id.recyclerView)
    val fab: FloatingActionButton by bindView<FloatingActionButton>(R.id.fab)
    private val items: List<*>? = null
    private val adapter: Adapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_test_select_photo)
        init()
    }

    private fun init() {
        //on click
        fab.setOnClickListener({ v ->
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {

        when (requestCode) {
            PICK_IMAGE -> {
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }


    companion object {
        @JvmField
        val hint = "TestSelectPhotoAct"
        val PICK_IMAGE = 0xc111
    }
}