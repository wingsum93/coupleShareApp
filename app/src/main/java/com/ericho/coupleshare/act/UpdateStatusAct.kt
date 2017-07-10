package com.ericho.coupleshare.act

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import android.widget.Toast
import butterknife.bindView

import com.ericho.coupleshare.R
import com.ericho.coupleshare.adapter.UploadPhotoAdapter
import com.ericho.coupleshare.mvp.Photo

class UpdateStatusAct : BasePermissionActivity() {

    val textView:TextView by bindView(R.id.textView)
    val recyclerView:RecyclerView by bindView(R.id.recyclerView)
    val fab:FloatingActionButton by bindView(R.id.fab)

    lateinit var layoutManager:RecyclerView.LayoutManager
    var items:ArrayList<Photo> = ArrayList<Photo>()
    lateinit var adapter:RecyclerView.Adapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_update_status)
        init();
    }

    private fun init() {
        adapter = UploadPhotoAdapter(this,items)
        layoutManager = GridLayoutManager(this,3)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        fab.setOnClickListener { _ ->  doUploadPhoto()}
        textView.text = getString(R.string.loading)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQ_ADD_PHOTO -> {
                if(resultCode == Activity.RESULT_OK){
                    doUploadPhoto()
                    fetchUploadList()
                }else{
                    showToastText("upload photo was cancelled!")
                }
            }
        }
    }

    /**
     * update the photo list
     */
    private fun fetchUploadList() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun doUploadPhoto() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        val REQ_ADD_PHOTO = 5555
    }

    fun AppCompatActivity.showToastText(string:String){
        Toast.makeText(this,string,Toast.LENGTH_LONG).show()
    }
}
