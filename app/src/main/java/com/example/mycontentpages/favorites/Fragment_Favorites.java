package com.example.mycontentpages.favorites;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycontentpages.R;
import com.example.mycontentpages.Utils.DataContainer;
import com.example.mycontentpages.data.Place;
import com.example.mycontentpages.attractionInfo.AttractionDetailsActivity;
import com.example.mycontentpages.home.Fragment_Home;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Favorites extends Fragment implements RecyclerViewInterface{

    View rootView;
    List<Place> favoritePlace =new ArrayList<>();
    DataContainer dataContainer = new DataContainer();


    public Fragment_Favorites() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        }

        //将dataconainer中取得的数据导入
        favoritePlace = dataContainer.getPlaceContainer();
        initView();
        return rootView;
    }

    private void initView() {
        RecyclerView recyclerView = rootView.findViewById(R.id.favorites_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //System.out.println("ceshi" + favoriteAttraction.size());//test
        Favorite_RecyclerViewAdapter favorite_recyclerViewAdapter = new Favorite_RecyclerViewAdapter(favoritePlace, getContext(), this);
        recyclerView.setAdapter(favorite_recyclerViewAdapter);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), AttractionDetailsActivity.class);
        intent.putExtra("name", favoritePlace.get(position).getName());
        intent.putExtra("description", favoritePlace.get(position).getDescription());
        intent.putExtra("picUrl", favoritePlace.get(position).getFirstPhoto());

        startActivity(intent);
    }
}