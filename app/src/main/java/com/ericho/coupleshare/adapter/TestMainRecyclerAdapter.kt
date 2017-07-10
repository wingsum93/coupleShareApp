package com.ericho.coupleshare.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ericho.coupleshare.R
import com.ericho.coupleshare.setting.model.AppTestBo
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.adapter
 */
class TestMainRecyclerAdapter constructor(context: Context,items:List<AppTestBo>):BaseRecyclerAdapter<AppTestBo, TestMainRecyclerAdapter.ViewHolder>(context, items) {


    private val onClickSubject = PublishSubject.create<AppTestBo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(getContext()).inflate(R.layout.grid_change_server, parent, false)
        val viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val o = items!![position]
        holder.imageView.setOnClickListener { _ -> onClickSubject.onNext(o) }
        holder.textView.setOnClickListener { _ -> onClickSubject.onNext(o) }
        holder.textView.text = o.displayName

    }

    fun getPositionClicks(): Observable<AppTestBo> {
        return onClickSubject
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textView = view.findViewById(R.id.text1) as TextView
        var imageView = view.findViewById(R.id.image) as TextView

    }


}