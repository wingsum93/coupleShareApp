package com.ericho.coupleshare.act

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.os.PersistableBundle
import android.provider.MediaStore
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import butterknife.bindView
import com.ericho.coupleshare.R
import com.ericho.coupleshare.adapter.UploadPhotoAdapter
import com.ericho.coupleshare.frag.AlertDialogFrag
import com.ericho.coupleshare.mvp.PhotoBo
import com.ericho.coupleshare.mvp.PhotosAddContract
import com.ericho.coupleshare.mvp.presenter.AddPhotoPresenter
import com.ericho.coupleshare.service.UploadPhotoService
import com.ericho.coupleshare.util.FileHelper
import com.ericho.coupleshare.util.safe
import timber.log.Timber
import java.io.File


class PhotoAddAct : BasePermissionActivity(), PhotosAddContract.View {

    val textView:TextView by bindView(R.id.textView)
    val recyclerView:RecyclerView by bindView(R.id.recyclerView)
    val fab:FloatingActionButton by bindView(R.id.fab)

    lateinit var layoutManager:RecyclerView.LayoutManager
    var uris:ArrayList<Uri> = ArrayList<Uri>()
    lateinit var adapter:UploadPhotoAdapter


    val presenter = AddPhotoPresenter(this)

    var mBoundService: UploadPhotoService? = null
    var mBound: Boolean = false
    lateinit var fileHelper:FileHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_photo_add)
        init(savedInstanceState)
        val mServiceBoundIntent = Intent(this, UploadPhotoService::class.java)
        this.bindService(mServiceBoundIntent,mServiceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.upload,menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun init(bundle:Bundle?) {
        if(bundle!=null){
            val x :ArrayList<Uri>? = bundle.getParcelableArrayList<Uri>("listdata")
            uris.addAll(x.safe())
        }
        textView.text = getString(R.string.loading)
        textView.visibility = View.GONE
        adapter = UploadPhotoAdapter(this, uris)
        layoutManager = GridLayoutManager(this,3)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        //listener
        fab.setOnClickListener { _ ->  showImageGallery()}
        adapter.setOnItemClickListener {
            position ->
            Timber.d("image clcik $position")

        }
        adapter.setOnItemLongClickListener {
            position->
            Timber.d("image long clcik $position")

            showConfirmDeleteDialog(uris[position])

            return@setOnItemLongClickListener true
        }

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
        val dialog = AlertDialogFrag()
//        dialog.title =
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
                    val uri = data!!.data
                    addMorePhoto(uri)
                }else{
                    showToastText("pick photo was cancelled!")
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun addMorePhoto(uri: Uri) {
        if(uris.contains(uri))
            showToastText("Photo already added!")
        else{
            uris.add(uri)
            fetchUploadList()
        }
    }

    fun doUploadPhoto(){
        fileHelper = FileHelper(this)
        Timber.d("click tick ${mBoundService}")
        if(mBoundService==null) return
        if(uris.isEmpty()) return
        val fileList:List<File> = fileHelper.convertUriToFile(uris)
        mBoundService?.uploadImage(fileList)
        this.finish()
    }
    fun fetchUploadList(){
        adapter.notifyDataSetChanged()
    }

    override fun setPresenter(presenter: PhotosAddContract.Presenter) {
        //
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        outState!!.putParcelableArrayList("listdata", uris)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun showImageGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

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
    }

    fun AppCompatActivity.showToastText(string:String){
        Toast.makeText(this,string,Toast.LENGTH_LONG).show()
    }
}
