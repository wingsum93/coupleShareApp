package com.ericho.coupleshare.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ericho.coupleshare.R
import com.ericho.coupleshare.setting.model.ServerBean
import com.ericho.coupleshare.util.DrawableUtil

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.adapter
 */
class ServerUrlAdapter constructor(context: Context ,items:List<ServerBean>?):BaseObjectAdapter<ServerBean> (context,items) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val i = getItem(position)
        val holder: ViewHolder
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.grid_change_server, parent, false)
            holder = ViewHolder(convertView)
            convertView!!.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        val drawable = DrawableUtil.getDrawble(getContext(), i.resourceInteger)
        holder.imageView!!.setImageDrawable(drawable)
        holder.text1!!.text = i.displayName


        return convertView
    }

    class ViewHolder(v: View) {
        var imageView: ImageView = v.findViewById<ImageView>(R.id.image) as ImageView
        var text1: TextView = v.findViewById<ImageView>(R.id.text1) as TextView

    }
}