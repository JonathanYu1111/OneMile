package com.example.mycontentpages.search;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mycontentpages.R;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private List<String> searchList;

    public SearchAdapter(List<String> searchList) {
        this.searchList = searchList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recent_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String search = searchList.get(position);
        holder.searchText.setText(search);
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView searchText;

        ViewHolder(View view) {
            super(view);
            searchText = view.findViewById(R.id.search_text);
        }
    }
}
