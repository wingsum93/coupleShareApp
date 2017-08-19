package com.ericho.coupleshare.act

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.provider.MediaStore
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import butterknife.bindView
import com.ericho.coupleshare.R
import com.ericho.coupleshare.adapter.UploadPhotoAdapter
import com.ericho.coupleshare.frag.ConfirmDialog
import com.ericho.coupleshare.interf.PermissionListener
import com.ericho.coupleshare.mvp.PhotoBo
import com.ericho.coupleshare.mvp.PhotosAddContract
import com.ericho.coupleshare.mvp.presenter.AddPhotoPresenter
import com.ericho.coupleshare.service.UploadPhotoService
import com.ericho.coupleshare.util.FileHelper
import com.ericho.coupleshare.util.ZoomImageHelper
import org.jetbrains.anko.toast
import timber.log.Timber
import java.io.File


class PhotoAddAct : BasePermissionActivity(), PhotosAddContract.View {

    val textView:TextView by bindView(R.id.textView)
    val recyclerView:RecyclerView by bindView(R.id.recyclerView)
    val fab:FloatingActionButton by bindView(R.id.fab)

    var layoutManager:RecyclerView.LayoutManager? = null
    var items:ArrayList<Uri> = ArrayList<Uri>()
    var adapter:UploadPhotoAdapter? = null


    val presenter = AddPhotoPresenter(this)

    var mBoundService: UploadPhotoService? = null
    var mBound: Boolean = false
    var fileHelper:FileHelper? = null
    var zoomImageHelper:ZoomImageHelper? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_photo_add)
        init()
        val mServiceBoundIntent = Intent(this, UploadPhotoService::class.java)
        this.bindService(mServiceBoundIntent,mServiceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.upload,menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun init() {

        textView.text = getString(R.string.loading)
        textView.visibility = View.GONE
        adapter = UploadPhotoAdapter(this, items)
        layoutManager = GridLayoutManager(this,3)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        //listener
        fab.setOnClickListener { _ ->  showImageGallery()}
        adapter!!.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            Timber.d("image clcik $position")
            zoomImageHelper?.zoomImageFromThumb(view, items[position])
        })
        adapter!!.setOnItemLongClickListener {
            position->
            Timber.d("image long clcik $position")

            showConfirmDeleteDialog(items[position])

            return@setOnItemLongClickListener true
        }
        zoomImageHelper = ZoomImageHelper.Builder(this)
                .setRootId(R.id.root)
                .setExpendViewId(R.id.photo_expand)
                .setDuration(resources.getInteger(android.R.integer.config_shortAnimTime))
                .build()

        presenter.start()

    }

    private val mServiceConnection = object : ServiceConnection {

        override fun onServiceDisconnected(name: ComponentName) {
            Timber.d("onServiceDisconnected ${name.className}")
            mBound = false
        }

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Timber.d("onServiceConnected ${name.className}")
            val myBinder = service as UploadPhotoService.MyBinder
            mBoundService = myBinder.service as UploadPhotoService
            mBound = true
        }
    }

    private fun showConfirmDeleteDialog(uri: Uri) {
        val dialog = ConfirmDialog.newInstance(getString(R.string.confirm),getString(R.string.confirm_to_delete))
        dialog.setConfirmRunnable {
            items.remove(uri)
            adapter?.notifyDataSetChanged()
        }
        dialog.show(supportFragmentManager,"confirm")

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId){
            R.id.menu_upload -> {
                doUploadPhoto()
                return true
            }
            else ->return super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {


        presenter.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            //select image gallery
            REQ_PICK_IMAGE -> {
                if(resultCode == Activity.RESULT_OK){
                    val x : ArrayList<Uri> = processPhotoIntent(data)
                    addMorePhoto(x)
                }else{
                  toast("pick photo was cancelled!")
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun processPhotoIntent(intent: Intent?): ArrayList<Uri> {
        val res = ArrayList<Uri>()


        val onePhoto:Boolean = intent!!.clipData==null || intent.clipData.itemCount==1
        if(onePhoto){
            res.add(intent!!.data)
        }
        else{
            for (i in 0 until intent!!.clipData.itemCount) {
                res.add(intent.clipData.getItemAt(i).uri)
            }
        }
        return res
    }

    private fun addMorePhoto(uri: Uri) {
        if(items.contains(uri))
            showToastText("Photo already added!")
        else{
            items.add(uri)
            fetchUploadList()
        }
    }
    private fun addMorePhoto(uri: ArrayList<Uri>) {

        val data = uri.filterNot {
            items.contains(it)
        }
        items.addAll(data)
        fetchUploadList()
    }

    fun doUploadPhoto(){
        fileHelper = FileHelper(this)
        Timber.d("click tick ${mBoundService}")
        if(mBoundService==null) return
        if(items.isEmpty()) return
        val fileList:List<File> = fileHelper!!.convertUriToFile(items)
        mBoundService?.uploadImage(fileList)
        this.finish()
    }
    fun fetchUploadList(){
        adapter?.notifyDataSetChanged()
    }

    override fun setPresenter(presenter: PhotosAddContract.Presenter) {
        //
    }

    override fun showImageGallery() {

        this.checkSelfPermission(REQ_PERMISSION_PICK_IMAGE,android.Manifest.permission.READ_EXTERNAL_STORAGE.toList(),object :PermissionListener{
            override fun onGranted() {
                _showImageGallery()
            }

            override fun onDenied(deniedPermission: List<String>) {
                showToastText(getString(R.string.permission_denied))
            }
        })
    }
    fun _showImageGallery(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        val str = getString(R.string.select_picture)
        startActivityForResult(Intent.createChooser(intent, str), REQ_PICK_IMAGE)
    }

    override fun showCamera() {

        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }

    }

    override fun showSyncing(sync: Boolean){
        val txt:String  = if(sync) getString(R.string.uploading) else getString(R.string.empty)
        textView.text =txt
    }

    override fun showPhotoList(photos: List<PhotoBo>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {
        unbindService(mServiceConnection)
        super.onDestroy()
    }

    companion object {
        val REQUEST_IMAGE_CAPTURE = 109
        val REQ_PICK_IMAGE = 101
        val REQ_PERMISSION_PICK_IMAGE = 102
    }

    fun AppCompatActivity.showToastText(string:String){
      toast(string)
    }
}
