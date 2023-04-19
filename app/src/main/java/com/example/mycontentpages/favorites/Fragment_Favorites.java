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
import com.example.mycontentpages.attractionInfo.Attraction;
import com.example.mycontentpages.attractionInfo.AttractionDetailsActivity;
import com.example.mycontentpages.home.Fragment_Home;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Favorites extends Fragment {

    View rootView;
    List<Attraction> favoriteAttraction=new ArrayList<>();


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
        initView();
        return rootView;
    }

    private void initView() {


        //test1: add data into favorites_rv
        //final version: get those data from backend
        List<String> picsURL=new ArrayList<>();//just for test data
        for(int i=0;i<30;i++){
            if(i%3==0){
                picsURL.add("https://images.unsplash.com/photo-1680095297939-5f69d7f139e9?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDF8YkRvNDhjVWh3bll8fGVufDB8fHx8&auto=format&fit=crop&w=600&q=60");
            }else if(i%3==1){
                picsURL.add("https://images.unsplash.com/photo-1681312407157-19ec16888a6f?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDEzfGJEbzQ4Y1Vod25ZfHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=600&q=60");
            }else {
                picsURL.add("https://images.unsplash.com/photo-1675111575738-80a06bbdb643?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDI1fGJEbzQ4Y1Vod25ZfHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=600&q=60");
            }
        }
        for(int i=0;i<20;i++){
            String name="place"+i;
            String description=name+"-"+name+"-"+name+"-"+name+"-"+name+"-"+name+"-"
                    +name+"-"+name+"-"+name+"-"+name+"-"+name+"-"+name+"-"+name+"-"
                    +name+"-"+name+"-"+name+"-"+name+"-"+name+"-" +name+"-"+name+"-"
                    +name+"-"+name+"-"+name+"-"+name;
            Attraction attraction=new Attraction(picsURL.get(i),name,description);
            favoriteAttraction.add(attraction);

           RecyclerView recyclerView= rootView.findViewById(R.id.favorites_rv);
           LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
           recyclerView.setLayoutManager(linearLayoutManager);
           Favorite_RecyclerViewAdapter favorite_recyclerViewAdapter=new Favorite_RecyclerViewAdapter(favoriteAttraction,getContext());
           recyclerView.setAdapter(favorite_recyclerViewAdapter);
           favorite_recyclerViewAdapter.setOnItemClickListener(new Favorite_RecyclerViewAdapter.OnItemClickListener() {
               @Override
               public void onItemClick(int position) {
                   Intent intent = new Intent(getActivity(), AttractionDetailsActivity.class);
                   intent.putExtra("attraction", favoriteAttraction.get(position));
                   startActivity(intent);
               }
           });
        }

    }
}