package com.ericho.coupleshare.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.ericho.coupleshare.R
import com.ericho.coupleshare.mvp.PhotoBo
import com.ericho.coupleshare.network.GlideApp
import com.ericho.coupleshare.network.GlideLoader
import com.ericho.coupleshare.util.NetworkUtil


/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.adapter
 */
class PhotoAdapter constructor(context: Context, items: List<PhotoBo>?) : BaseRecyclerAdapter<PhotoBo, PhotoAdapter.ViewHolder>(context, items) {

    private var mListener: OnItemClickListener? = null
    private var mLongClickListener: OnItemLongClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(getContext()).inflate(R.layout.row_photo, parent, false)
        val holder = ViewHolder(view)
        return holder

    }


    fun setOnItemClickListener(li: OnItemClickListener) {
        mListener = li
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        mLongClickListener = listener
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items!!.get(position)

        GlideLoader.loadNormal(
                getContext(),
                holder.imageView,
                NetworkUtil.getUrl(getContext(), item.suffixUrl!!)
                )
        holder.imageView.setOnClickListener {
            mListener?.onItemClick(null, holder.itemView, position, getItemId(position))
        }
        holder.imageView.setOnLongClickListener {
            mLongClickListener?.onItemLongClick(null, holder.itemView, position, getItemId(position))
            true
        }

    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView = view.findViewById<ImageView>(R.id.imageView)

    }

    companion object {
        val TYPE_NORMAL = 100
        val TYPE_HEADER = 101
    }
}