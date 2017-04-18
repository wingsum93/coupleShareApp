package com.ericho.coupleshare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by EricH on 18/4/2017.
 */

public abstract class BaseObjectAdapter<Type> extends BaseAdapter {
    private Context context;
    private boolean highlight;
    private int lastPosition;
    private List<Type> items;
    protected LayoutInflater layoutInflater;

    public BaseObjectAdapter(Context context, List<Type> items) {
        this.items = items;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public BaseObjectAdapter(Context context, List<Type> items, boolean highlight, int lastPosition) {
        this.items = items;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.highlight = highlight;
        this.lastPosition = lastPosition;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Type getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public List<Type> update(List<Type> items) {
        this.items = items;
        notifyDataSetChanged();
        return this.items;
    }

    public Context getContext() {
        return context;
    }

    public List<Type> getAllItem() {
        return items;
    }

    public void setItems(List<Type> items) {
        this.items = items;
    }

//    @Override
//    public void setText(TextView t, @Nullable String s) {
//        if(s==null){
//            s = "";
//        }
//        t.setText(s);
//    }
}
