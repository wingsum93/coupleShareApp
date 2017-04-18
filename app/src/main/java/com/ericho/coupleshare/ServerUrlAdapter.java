package com.ericho.coupleshare;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ericho.coupleshare.adapter.BaseObjectAdapter;
import com.ericho.coupleshare.setting.model.ServerBean;
import com.ericho.coupleshare.util.DrawableUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by EricH on 18/4/2017.
 */

public class ServerUrlAdapter extends BaseObjectAdapter<ServerBean> {
    public ServerUrlAdapter(Context context, List<ServerBean> items) {
        super(context, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ServerBean i = getItem(position);
        ViewHolder holder;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.grid_change_server,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        Drawable drawable = DrawableUtil.getDrawble(getContext(),i.getResourceInteger());
        holder.imageView.setImageDrawable(drawable);
        holder.text1.setText(i.getDisplayName());


        return convertView;
    }

    static class ViewHolder{
        @BindView(R.id.image) protected ImageView imageView;
        @BindView(R.id.text1) protected TextView text1;
        ViewHolder(View v){
            ButterKnife.bind(this,v);
        }
    }
}
