package com.ericho.coupleshare.act

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import android.widget.Toast
import butterknife.bindView

import com.ericho.coupleshare.R
import com.ericho.coupleshare.adapter.UploadPhotoAdapter
import com.ericho.coupleshare.mvp.*
import com.ericho.coupleshare.mvp.presenter.AddPhotoPresenter
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import com.ericho.coupleshare.util.safe
import com.ericho.coupleshare.util.toFileList
import timber.log.Timber
import java.io.File


class PhotoAddAct : BasePermissionActivity(), PhotosAddContract.View {

    val textView:TextView by bindView(R.id.textView)
    val recyclerView:RecyclerView by bindView(R.id.recyclerView)
    val fab:FloatingActionButton by bindView(R.id.fab)

    lateinit var layoutManager:RecyclerView.LayoutManager
    var items:ArrayList<Uri> = ArrayList<Uri>()
    lateinit var adapter:UploadPhotoAdapter

    val presenter = AddPhotoPresenter(this)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_photo_add)
        init(savedInstanceState);
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.upload,menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun init(bundle:Bundle?) {
        if(bundle!=null){
            val x :ArrayList<Uri>? = bundle.getParcelableArrayList<Uri>("listdata")
            items.addAll(x.safe)
        }
        textView.text = getString(R.string.loading)

        adapter = UploadPhotoAdapter(this,items)
        layoutManager = GridLayoutManager(this,3)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        //listener
        fab.setOnClickListener { _ ->  showImageGallery()}
        adapter.imageClickListener = object :UploadPhotoAdapter.OnImageClickListener{
            override fun onImageClick(position: Int) = Timber.d("image clcik $position")
        }
        adapter.imageLongClickListener = object :UploadPhotoAdapter.OnImageLongClickListener{
            override fun onImageLongClick(position: Int): Boolean {
                Timber.d("image long clcik $position")
                return true
            }
        }

        presenter.start()
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
            PhotoAddAct.REQ_ADD_PHOTO -> {
                if(resultCode == Activity.RESULT_OK){
                    doUploadPhoto()
                    fetchUploadList()
                }else{
                    showToastText("upload photo was cancelled!")
                }
            }
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
        items.add(uri)
        fetchUploadList()
    }

    fun doUploadPhoto(){

        val fileList:List<File> = items.toFileList()


    }
    fun fetchUploadList(){
        adapter.notifyDataSetChanged()
    }

    override fun setPresenter(presenter: PhotosAddContract.Presenter) {
        //
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        outState!!.putParcelableArrayList("listdata",items)
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

    override fun showPhotoList(photos: List<Photo>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        val REQ_ADD_PHOTO = 125
        val REQUEST_IMAGE_CAPTURE = 109
        val REQ_PICK_IMAGE = 101
    }

    fun AppCompatActivity.showToastText(string:String){
        Toast.makeText(this,string,Toast.LENGTH_LONG).show()
    }
}
