package com.ericho.coupleshare.adapter

import android.content.Context
import android.view.LayoutInflater
import android.widget.BaseAdapter

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.adapter
 */
open abstract class BaseObjectAdapter<Type> constructor(context: Context, items: List<Type>?):BaseAdapter() {
    private var context: Context? = null
    private var items: List<Type>? = null
    protected var layoutInflater: LayoutInflater

    init {
        this.items = items
        this.context = context
        layoutInflater = LayoutInflater.from(context)
    }


    override fun getCount(): Int {
        return items!!.size
    }

    override fun getItem(position: Int): Type {
        return items!![position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    fun update(items: List<Type>): List<Type> {
        this.items = items
        notifyDataSetChanged()
        return this.items as List<Type>
    }

    fun getContext(): Context? {
        return context
    }

    fun getAllItem(): List<Type>? {
        return items
    }

    fun setItems(items: List<Type>?) {
        this.items = items
    }

}