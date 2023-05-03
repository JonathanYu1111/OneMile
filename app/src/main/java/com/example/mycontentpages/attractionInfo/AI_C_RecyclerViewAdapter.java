package com.example.mycontentpages.attractionInfo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycontentpages.R;

import java.util.List;

public class AI_C_RecyclerViewAdapter extends RecyclerView.Adapter<AI_C_RecyclerViewAdapter.MyViewHolder> {
    private List<Comment> data;
    private Context context;

    public AI_C_RecyclerViewAdapter(List<Comment> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public AI_C_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=View.inflate(context, R.layout.item_attr_info_comment,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AI_C_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.tv.setText(data.get(position).getUsername()+":"+data.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.comment_tv);
        }
    }
}
