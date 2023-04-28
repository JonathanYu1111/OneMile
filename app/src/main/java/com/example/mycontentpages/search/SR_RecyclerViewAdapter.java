package com.example.mycontentpages.search;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycontentpages.R;
import com.example.mycontentpages.data.Place;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SR_RecyclerViewAdapter extends RecyclerView.Adapter<SR_RecyclerViewAdapter.MyViewHolder>{
    List<Place> data;
    Context context;

    public SR_RecyclerViewAdapter(List<Place> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=View.inflate(context, R.layout.item_search_result,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name_tv.setText(data.get(position).getName());
        holder.description_tv.setText(data.get(position).getDescription());
        Picasso.get()
                .load(data.get(position).getUrl())
                .into(holder.img_tv);
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name_tv;
        private TextView description_tv;
        private ImageView img_tv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name_tv=itemView.findViewById(R.id.sr_name);
            description_tv=itemView.findViewById(R.id.sr_description);
            img_tv=itemView.findViewById(R.id.sr_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(getBindingAdapterPosition());
                }
            });
        }
    }

    private OnItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}
