package com.ericho.coupleshare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by steve_000 on 28/4/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.adapter
 */
public abstract class BaseRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected List<T> items;
    private Context context;

    public BaseRecyclerAdapter(Context context, List<T> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<T> getItems() {
        return items;
    }

    public Context getContext() {
        return context;
    }

    public List<T> update(List<T> list){
        this.items = list;
        this.notifyDataSetChanged();
        return this.items ;
    }
}
