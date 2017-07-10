package com.ericho.coupleshare.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.ericho.coupleshare.R
import com.ericho.coupleshare.mvp.Photo

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.adapter
 */
class PhotoAdapter constructor(context: Context,items:List<Photo>?) : BaseRecyclerAdapter<Photo, PhotoAdapter.ViewHolder>(context, items){


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(getContext()).inflate(R.layout.row_photo, parent, false)
        val holder = ViewHolder(view)
        return holder

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView?.setImageResource(R.drawable.ic_menu_manage)

    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView = view.findViewById(R.id.imageView) as ImageView

    }
}