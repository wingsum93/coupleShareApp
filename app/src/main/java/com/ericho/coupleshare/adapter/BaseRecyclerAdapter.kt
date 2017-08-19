package com.ericho.coupleshare.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.adapter
 */
abstract class BaseRecyclerAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH> {

    var items: List<T>? = null
    private val context: Context

    constructor(context :Context , items:List<T>? ){
        this.items = items
        this.context = context
    }
    constructor(context :Context , items:Array<T> ):this(context,items.toList())



    override fun getItemCount(): Int {
        return items?.size ?:-1
    }


    fun getContext(): Context {
        return context
    }

    fun update(list: List<T>): List<T> {
        this.items = list
        this.notifyDataSetChanged()
        return this.items as List<T>
    }

}