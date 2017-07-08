package com.ericho.coupleshare.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.adapter
 */
open abstract class BaseRecyclerAdapter<T, VH : RecyclerView.ViewHolder> constructor(context :Context , items:List<T>? ): RecyclerView.Adapter<VH>()  {

    var items: List<T>? = null
    private val context: Context

    init {
        this.items = items
        this.context = context
    }


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