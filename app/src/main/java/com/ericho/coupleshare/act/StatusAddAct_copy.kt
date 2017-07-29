package com.ericho.coupleshare.act

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.View
import android.widget.*
import butterknife.bindView
import com.bumptech.glide.Glide
import com.ericho.coupleshare.App
import com.ericho.coupleshare.R
import com.ericho.coupleshare.eventbus.StatusEvent
import com.ericho.coupleshare.http.StatusNoticeManager
import com.ericho.coupleshare.http.model.BaseSingleResponse
import com.ericho.coupleshare.model.StatusTO
import com.ericho.coupleshare.util.FileHelper
import com.ericho.coupleshare.util.AHttpHelper
import com.ericho.coupleshare.util.NetworkUtil
import com.ericho.coupleshare.util.ZoomImageHelper
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import java.io.File


class StatusAddAct_copy : BasePermissionActivity() {

    val imageView:ImageView by bindView(R.id.imageView)
    val progressBar:ProgressBar by bindView(R.id.progressBar)
    val edt_title:EditText by bindView(R.id.edt_title)
    val edt_content:EditText by bindView(R.id.edt_content)
    val btn_ok:Button by bindView(R.id.btn_ok)
    val btn_cancel:Button by bindView(R.id.btn_cancel)

    var item: StatusTO = StatusTO()

    val manager:StatusNoticeManager = StatusNoticeManager()

    lateinit var zoomHelper:ZoomImageHelper
    lateinit var fileHelper:FileHelper
    lateinit var httpHelper: AHttpHelper<BaseSingleResponse<Unit>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_status_notice_add)
        init(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.upload,menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun init(bundle:Bundle?) {

        zoomHelper = ZoomImageHelper.Builder(this)
                .setRootId(R.id.container)
                .setExpendViewId(R.id.expanded_image)
                .setDuration(resources.getInteger(android.R.integer.config_shortAnimTime))
                .build()
        fileHelper = FileHelper(this)
        httpHelper = AHttpHelper.Builder<BaseSingleResponse<Unit>>()
                .setSuccessMethod(this::success)
                .setTransformMethod { string -> App.gson.fromJson(string,object :TypeToken<BaseSingleResponse<Unit>>(){}.type)  }
                .setFail { _call, e ->
                    Timber.w(e)
                    showToastText("${e.message}")
                    showProgress(false)
                    lockUi(false)
                }.build()
        //listener
        btn_ok.setOnClickListener { _ ->
            item.title = edt_title.text.toString()
            item.content = edt_content.text.toString()
            try {
                val file = fileHelper.convertUri(item.uri!!)
                file.lastModified()
                uploadStatueNotice(file)
            }catch (e:Exception){
                showToastText("${e.message}")
                Timber.w(e)
            }

        }
        btn_cancel.setOnClickListener { _ ->  finish()}
        imageView.setOnClickListener { _ ->  showImageGallery()}
        imageView.setOnLongClickListener { _ ->
            if(item.uri!=null){
                zoomHelper.zoomImageFromThumb(imageView,item.uri!!)
            }
            return@setOnLongClickListener true

        }

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when(requestCode){
            //select image gallery
            REQ_PICK_IMAGE -> {
                if(resultCode == Activity.RESULT_OK){
                    val uri = data!!.data
                    replacePhotoUri(uri)
                }else{
                    showToastText("pick photo was cancelled!")
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }

    }

    private fun replacePhotoUri(uri: Uri) {
        item.uri = uri
        loadImageBitmapFromUri()
    }
    fun loadImageBitmapFromUri(){
        Glide.with(this)
                .load(item.uri)
                .skipMemoryCache(true)
                .into(imageView)
    }
    fun showProgress( active:Boolean){
        progressBar.visibility = if (active) View.VISIBLE else View.GONE
    }
    fun lockUi(release:Boolean){
        val lock = !release
        edt_title.isEnabled = lock
        edt_content.isEnabled = lock
        btn_ok.isEnabled = lock
        btn_cancel.isEnabled = lock
    }

    fun uploadStatueNotice(file: File){

        //change state to
        lockUi(true)
        showProgress(true)

        //do upload

        val request =
        NetworkUtil.status_add(edt_title.text.toString(),edt_content.text.toString(),file)


        httpHelper.run(request)
    }

    private fun success(call: Call, res: BaseSingleResponse<Unit>) {
            //success
            showToastText(getString(R.string.upload_success))
            showProgress(false)
            lockUi(false)
            EventBus.getDefault().post(StatusEvent())
            this.finish()
    }

    fun showImageGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        val str = getString(R.string.select_picture)
        startActivityForResult(Intent.createChooser(intent, str), REQ_PICK_IMAGE)
    }
    fun _showImageGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        val str = getString(R.string.select_picture)
        startActivityForResult(Intent.createChooser(intent, str), REQ_PICK_IMAGE)
    }

    fun showCamera() {

        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }

    }


    override fun onBackPressed() {
        zoomHelper.onBackPress()
        super.onBackPressed()
    }

    companion object {
        val REQUEST_IMAGE_CAPTURE = 109
        val REQ_PICK_IMAGE = 101
    }

    fun AppCompatActivity.showToastText(string:String){
        Toast.makeText(this,string,Toast.LENGTH_LONG).show()
    }
}
