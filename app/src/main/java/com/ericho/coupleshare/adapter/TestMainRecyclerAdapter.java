package com.ericho.coupleshare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.ericho.coupleshare.R;
import com.ericho.coupleshare.setting.model.AppTestBo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by EricH on 19/4/2017.
 */

public class TestMainRecyclerAdapter extends BaseRecyclerAdapter<AppTestBo, TestMainRecyclerAdapter.ViewHolder> {
    private final PublishSubject<AppTestBo> onClickSubject = PublishSubject.create();

    public TestMainRecyclerAdapter(Context context, List<AppTestBo> items) {
        super(context, items);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.grid_change_server, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AppTestBo o = getItems().get(position);
        holder.imageView.setOnClickListener(v -> onClickSubject.onNext(o));
        holder.textView.setOnClickListener(v -> onClickSubject.onNext(o));
        holder.textView.setText(o.getDisplayName());

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public Observable<AppTestBo> getPositionClicks() {
        return onClickSubject;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text1)
        TextView textView;
        @BindView(R.id.image)
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
