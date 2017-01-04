package com.xxz.magnet.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.xxz.magnet.R;

import java.util.List;

/**
 * Created by joyce on 2017/1/3.
 */

public class SSAdapter extends RecyclerView.Adapter<SSAdapter.MyViewHolder> {
    private List<String> data;

    public SSAdapter( List<String> pData) {
        data = pData;
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_ss,
                parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        Picasso.with(myViewHolder.title.getContext()).load(data.get(position)).into(myViewHolder.title);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView title;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (ImageView) itemView.findViewById(R.id.item_image);
        }
    }
}
