package com.ericho.coupleshare.adapter

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.ericho.coupleshare.R


/**
 * Created by steve_000 on 9/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.adapter
 */
class UploadPhotoAdapter constructor(context:Context, items:List<Uri>):BaseRecyclerAdapter<Uri, UploadPhotoAdapter.ViewHolder>(context, items) {

    var imageItemClickListener:AdapterView.OnItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->  }
    var onImageLongClick :((position:Int)->Boolean) ={false}


    override fun onBindViewHolder(holder: UploadPhotoAdapter.ViewHolder?, position: Int) {


        val item = items!!.get(position)

//        val bitmap = MediaStore.Images.Media.getBitmap(getContext().contentResolver, item)
//        holder!!.img.setImageBitmap(bitmap)

        Glide.with(getContext())
                .load(item)
                .into(holder!!.img)

        holder.img.setOnClickListener { imageItemClickListener.onItemClick(null,holder.img,position,getItemId(position)) }
        holder.img.setOnLongClickListener {
            return@setOnLongClickListener onImageLongClick.invoke(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): UploadPhotoAdapter.ViewHolder {
        val v = LayoutInflater.from(getContext()).inflate(R.layout.row_photo,parent,false)
        return ViewHolder(v)
    }

    fun setOnItemLongClickListener(listener:(position:Int)->Boolean){
        this.onImageLongClick = listener
    }
    fun setOnItemClickListener(listener:AdapterView.OnItemClickListener){
        this.imageItemClickListener = listener
    }

    class ViewHolder constructor(view:View):RecyclerView.ViewHolder(view){
        val img:ImageView = view.findViewById(R.id.imageView) as ImageView
    }
}