package com.example.mycontentpages.attractionInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mycontentpages.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AI_P_Adapter extends RecyclerView.Adapter<AI_P_Adapter.MyViewHolder> {
    private List<String> attPicsUrl;
    private LayoutInflater mInflater;


    public AI_P_Adapter(Context context, List<String> photoList) {
        mInflater = LayoutInflater.from(context);
        attPicsUrl = photoList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_image, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String imageUrl = attPicsUrl.get(position);
        Picasso.get()
                .load(imageUrl)
                .into(holder.photoImageView);

    }

    @Override
    public int getItemCount() {
        return attPicsUrl==null?0:attPicsUrl.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView photoImageView;

        MyViewHolder(View itemView) {
            super(itemView);
            photoImageView = itemView.findViewById(R.id.photoImageView);
        }
    }
}