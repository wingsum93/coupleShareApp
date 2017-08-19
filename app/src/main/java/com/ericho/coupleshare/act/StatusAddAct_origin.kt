package com.ericho.coupleshare.act

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.graphics.Point
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import butterknife.bindView
import com.bumptech.glide.Glide
import com.ericho.coupleshare.R
import com.ericho.coupleshare.network.StatusNoticeManager
import com.ericho.coupleshare.model.StatusTO
import com.ericho.coupleshare.util.float
import kotlinx.android.synthetic.main.act_status_notice_add.*
import org.jetbrains.anko.toast


class StatusAddAct_origin : BasePermissionActivity() {

    val imageView:ImageView by bindView(R.id.imageView)
    val btn_ok:Button by bindView(R.id.btn_ok)
    val btn_cancel:Button by bindView(R.id.btn_cancel)

    var item: StatusTO = StatusTO()

    val manager:StatusNoticeManager = StatusNoticeManager()

    var mCurrentAnimator:Animator? = null
    var mShortAnimationDuration:Int? = null


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




        btn_ok.setOnClickListener { _ ->
            item.title = edt_title.text.toString()
            item.content = edt_content.text.toString()
            save()
        }
        btn_cancel.setOnClickListener { _ ->  finish()}
        imageView.setOnClickListener { _ ->  showImageGallery()}
        imageView.setOnLongClickListener { _ ->
            if(item.uri!=null){
                zoomImageFromThumb(imageView,item.uri!!)
            }
            return@setOnLongClickListener true

        }

        mShortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when(requestCode){
            //select image gallery
            REQ_PICK_IMAGE -> {
                if(resultCode == Activity.RESULT_OK){
                    val uri = data!!.data
                    replacePhotoUri(uri)
                }else{
                    toast("pick photo was cancelled!")
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
        val bitmap = contentResolver.openInputStream(item.uri)
        Glide.with(this)
                .load(item.uri)
                .into(imageView)
    }

    fun save(){
        manager.save(item)
    }




    fun showImageGallery() {
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

    fun zoomImageFromThumb(thumbView: View, imageUri:Uri){
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator?.cancel()
        }

        // Load the high-resolution "zoomed-in" image.
        val expandedImageView = findViewById(
                R.id.expanded_image) as ImageView
        Glide.with(this)
                .load(imageUri)
                .into(expandedImageView)

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        val startBounds = Rect()
        val finalBounds = Rect()
        val globalOffset = Point()

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds)
        findViewById(R.id.container)
                .getGlobalVisibleRect(finalBounds, globalOffset)
        startBounds.offset(-globalOffset.x, -globalOffset.y)
        finalBounds.offset(-globalOffset.x, -globalOffset.y)

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        val startScale: Float
        if ((finalBounds.width() ).div(finalBounds.height()) > (startBounds.width() ).div(startBounds.height())) {
            // Extend start bounds horizontally
            startScale = startBounds.height() .toFloat() / finalBounds.height()
            val startWidth:Int = startScale.times(finalBounds.width().float).toInt()
            val deltaWidth:Int = ((startWidth.minus(startBounds.width().float)) / 2) .toInt()
            startBounds.left -= deltaWidth
            startBounds.right += deltaWidth
        } else {
            // Extend start bounds vertically
            startScale = startBounds.width() .toFloat() / finalBounds.width()
            val startHeight:Int  = (startScale * finalBounds.height()).toInt()
            val deltaHeight:Int = (((startHeight - startBounds.height().float)) / 2).toInt()
            startBounds.top -= deltaHeight
            startBounds.bottom += deltaHeight
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.alpha = 0f
        expandedImageView.visibility = View.VISIBLE

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.pivotX = 0f
        expandedImageView.pivotY = 0f

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        val set = AnimatorSet()
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left.toFloat(), finalBounds.left.toFloat()))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top.toFloat(), finalBounds.top.toFloat()))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f)).with(ObjectAnimator.ofFloat(expandedImageView,
                View.SCALE_Y, startScale, 1f))
        set.setDuration(mShortAnimationDuration!!.toLong())
        set.interpolator = DecelerateInterpolator()
        set.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                mCurrentAnimator = null
            }

            override fun onAnimationCancel(animation: Animator) {
                mCurrentAnimator = null
            }
        })
        set.start()
        mCurrentAnimator = set

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        val startScaleFinal = startScale
        expandedImageView.setOnClickListener {
            if (mCurrentAnimator != null) {
                mCurrentAnimator?.cancel()//for cancelling the zomming
            }

            // Animate the four positioning/sizing properties in parallel,
            // back to their original values.
            val set = AnimatorSet()
            set.play(ObjectAnimator
                    .ofFloat(expandedImageView, View.X, startBounds.left.toFloat()))
                    .with(ObjectAnimator
                            .ofFloat(expandedImageView,
                                    View.Y, startBounds.top.toFloat()))
                    .with(ObjectAnimator
                            .ofFloat(expandedImageView,
                                    View.SCALE_X, startScaleFinal))
                    .with(ObjectAnimator
                            .ofFloat(expandedImageView,
                                    View.SCALE_Y, startScaleFinal))
            set.setDuration(mShortAnimationDuration!!.toLong())
            set.interpolator = DecelerateInterpolator()
            set.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    thumbView.alpha = 1f
                    expandedImageView.visibility = View.GONE
                    mCurrentAnimator = null
                }

                override fun onAnimationCancel(animation: Animator) {
                    thumbView.alpha = 1f
                    expandedImageView.visibility = View.GONE
                    mCurrentAnimator = null
                }
            })
            set.start()
            mCurrentAnimator = set
        }
    }


    companion object {
        val REQUEST_IMAGE_CAPTURE = 109
        val REQ_PICK_IMAGE = 101
    }


}
