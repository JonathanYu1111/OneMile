package com.example.mycontentpages.favorites;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycontentpages.attractionInfo.Attraction;
import com.example.mycontentpages.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Favorite_RecyclerViewAdapter extends RecyclerView.Adapter<Favorite_RecyclerViewAdapter.MyViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    private List<Attraction> data;
    private Context context;

    public Favorite_RecyclerViewAdapter(List<Attraction> data, Context context,RecyclerViewInterface recyclerViewInterface) {
        this.data = data;
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public Favorite_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view=View.inflate(context, R.layout.item_favorites_attraction,null);
//        return new MyViewHolder(view);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_favorites_attraction,parent,false);
        return new Favorite_RecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Favorite_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.tv_name.setText(data.get(position).getName());
        holder.tv_description.setText(data.get(position).getDescription());
        Picasso.get()
                .load(data.get(position).getPicURL())
                .into(holder.tv_img);
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name;
        private TextView tv_description;
        private ImageView tv_img;
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.f3_tv_name);
            tv_description=itemView.findViewById(R.id.f3_tv_description);
            tv_img=itemView.findViewById(R.id.f3_tv_img);

//            View.OnClickListener listener=new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onItemClickListener.onItemClick(getBindingAdapterPosition());
//                }
//            };
//            tv_img.setOnClickListener(listener);
//            tv_name.setOnClickListener(listener);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null) {
                        int pos = getAbsoluteAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
