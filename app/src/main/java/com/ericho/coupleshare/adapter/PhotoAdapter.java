package com.ericho.coupleshare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ericho.coupleshare.R;
import com.ericho.coupleshare.mvp.Photo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by steve_000 on 23/6/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.adapter
 */

public class PhotoAdapter extends BaseRecyclerAdapter<Photo,PhotoAdapter.ViewHolder> {


    public PhotoAdapter(Context context, List items) {
        super(context, items);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.row_photo,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageView.setImageResource(R.drawable.ic_menu_manage);

    }


    protected static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.imageView)
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
