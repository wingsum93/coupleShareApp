package com.ericho.coupleshare.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ericho.coupleshare.R
import com.ericho.coupleshare.http.OkHttpImpl
import com.ericho.coupleshare.mvp.StatusBo
import com.ericho.coupleshare.util.getUrl
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.adapter
 */
class StatusAdapter constructor(context: Context, items:List<StatusBo>?) : BaseRecyclerAdapter<StatusBo, StatusAdapter.ViewHolder>(context, items){

    var imageClickListener: AdapterView.OnItemClickListener = AdapterView.OnItemClickListener { _, view, position, _ ->  }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(getContext()).inflate(R.layout.row_status, parent, false)
        val holder = ViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items!!.get(position)
        Glide.with(getContext())
                .load(getContext().getUrl(item.photoUrlSuffix!!))
                .skipMemoryCache(true)
                .dontAnimate()
                .into(holder.imageView)


        holder.title.text = item.title
        holder.content.text = item.content

        holder.imageView.setOnClickListener {
            imageClickListener.onItemClick(null,holder.imageView,position,getItemId(position))
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: CircleImageView = view.findViewById(R.id.imageView) as CircleImageView
        var title: TextView = view.findViewById(R.id.title) as TextView
        var content: TextView = view.findViewById(R.id.content) as TextView

    }
}