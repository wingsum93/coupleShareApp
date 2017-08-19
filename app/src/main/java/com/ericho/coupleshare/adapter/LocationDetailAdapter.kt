package com.ericho.coupleshare.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ericho.coupleshare.R
import com.ericho.coupleshare.model.LocationTo

/**
 * Created by steve_000 on 4/8/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.adapter
 */
class LocationDetailAdapter: BaseRecyclerAdapter<LocationTo, LocationDetailAdapter.ViewHolder> {


    constructor(context: Context,items:List<LocationTo>):super(context, items){

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(getContext()).inflate(R.layout.row_loc_pub,parent,false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        holder!!.txt_id.text = "#$position"
        holder.txt_date.text = "2015-05-22 10:22"
    }

    inner class ViewHolder:RecyclerView.ViewHolder{
        var txt_id :TextView
        var txt_date :TextView

        constructor(view:View ) : super(view) {
            txt_id = view.findViewById(R.id.id) as TextView
            txt_date = view.findViewById(R.id.date_hint) as TextView
        }
    }
}