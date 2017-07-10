package com.ericho.coupleshare.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.ericho.coupleshare.R
import com.ericho.coupleshare.mvp.Photo

/**
 * Created by steve_000 on 9/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.adapter
 */
class UploadPhotoAdapter constructor(context:Context, items:List<Photo>):BaseRecyclerAdapter<Photo, UploadPhotoAdapter.ViewHolder>(context, items) {
    override fun onBindViewHolder(holder: UploadPhotoAdapter.ViewHolder?, position: Int) {


        val item = items!!.get(position)

        holder!!.img.setImageDrawable(Drawable.createFromPath(item.path))
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): UploadPhotoAdapter.ViewHolder {
        val v = LayoutInflater.from(getContext()).inflate(R.layout.row_photo,parent)
        return ViewHolder(v)
    }



    class ViewHolder constructor(view:View):RecyclerView.ViewHolder(view){
        val img:ImageView = view.findViewById(R.id.imageView) as ImageView
    }
}