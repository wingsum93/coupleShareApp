package com.ericho.coupleshare.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.graphics.Point
import android.graphics.Rect
import android.net.Uri
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.ericho.coupleshare.R
import timber.log.Timber

/**
 * Created by steve_000 on 17/7/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.util
 */
class ZoomImageHelper {

    val rootId : Int
    val expendViewId :Int

    var mCurrentAnimator :Animator? = null

    val mShortAnimationDuration :Int

    val mActivity:Activity

    constructor(rootId:Int,expendViewId:Int,duration:Int,context: Activity){
        this.rootId = rootId
        this.expendViewId = expendViewId
        this.mShortAnimationDuration = duration
        this.mActivity = context
    }

    class Builder(val context: Activity){

        var rootId : Int? = null
        var expendViewId :Int? = null


        var mShortAnimationDuration :Int? = null


        fun setRootId(id:Int) :Builder{
            this.rootId = id
            return this
        }
        fun setExpendViewId(id:Int):Builder{
            this.expendViewId = id
            return this
        }

        fun setDuration(duration:Int):Builder{
            this.mShortAnimationDuration = duration
            return this
        }

        fun build(): ZoomImageHelper {
            val x = ZoomImageHelper(
                    rootId = rootId!!,
                    expendViewId = expendViewId!!,
                    duration = mShortAnimationDuration!!,
                    context = context
            )


            return x
        }
    }
    fun findViewById(viewId:Int):View{
        return mActivity.findViewById(viewId)
    }

    fun zoomImageFromThumb(thumbView: View, imageUri: Uri){
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator?.cancel()
        }

        // Load the high-resolution "zoomed-in" image.
        val expandedImageView = findViewById(
                R.id.expanded_image) as ImageView
        Glide.with(mActivity)
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
                mCurrentAnimator?.cancel()//for cancelling the zooming
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

    fun onBackPress():Boolean{
        Timber.d("helper onBackPress")
        mCurrentAnimator?.cancel()
        return true
    }
}