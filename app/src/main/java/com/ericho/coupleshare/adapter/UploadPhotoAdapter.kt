package com.ericho.coupleshare.adapter

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.ericho.coupleshare.R
import android.provider.MediaStore
import android.graphics.Bitmap
import com.bumptech.glide.Glide


/**
 * Created by steve_000 on 9/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.adapter
 */
class UploadPhotoAdapter constructor(context:Context, items:List<Uri>):BaseRecyclerAdapter<Uri, UploadPhotoAdapter.ViewHolder>(context, items) {

    var imageClickListener:OnImageClickListener? = null
    var imageLongClickListener:OnImageLongClickListener? = null


    override fun onBindViewHolder(holder: UploadPhotoAdapter.ViewHolder?, position: Int) {


        val item = items!!.get(position)

//        val bitmap = MediaStore.Images.Media.getBitmap(getContext().contentResolver, item)
//        holder!!.img.setImageBitmap(bitmap)

        Glide.with(getContext())
                .load(item)
                .into(holder!!.img)

        holder.img.setOnClickListener { imageClickListener?.onImageClick(position) }
        holder.img.setOnLongClickListener {
            return@setOnLongClickListener imageLongClickListener?.onImageLongClick(position) ?:false
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): UploadPhotoAdapter.ViewHolder {
        val v = LayoutInflater.from(getContext()).inflate(R.layout.row_photo,parent,false)
        return ViewHolder(v)
    }
    interface OnImageClickListener{
        fun onImageClick(position:Int);
    }
    interface OnImageLongClickListener{
        fun onImageLongClick(position:Int) :Boolean
    }


    class ViewHolder constructor(view:View):RecyclerView.ViewHolder(view){
        val img:ImageView = view.findViewById(R.id.imageView) as ImageView
    }
}