package com.xxz.magnet.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.xxz.magnet.R;

import java.util.List;

/**
 * Created by joyce on 2017/1/3.
 */

public class SSAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> data;

    public SSAdapter(Context context, List<String> pData) {
        this.mContext = context;
        data = pData;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup pViewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_item_ss, null);
            viewHolder.ss = (ImageView) convertView.findViewById(R.id.item_image);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(mContext).load(data.get(position)).into(viewHolder.ss);
        return convertView;
    }

    private class ViewHolder {
        public ImageView ss;
    }
}
