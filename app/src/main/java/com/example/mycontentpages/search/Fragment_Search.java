package com.example.mycontentpages.search;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class Fragment_Search extends Fragment {

    View rootView;
    List<String> recentSearch=new ArrayList<>();

    List<Attraction> searchResult=new ArrayList<>();

    public Fragment_Search() {
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
            rootView = inflater.inflate(R.layout.fragment_search, container, false);
        }
        initView();
        return rootView;


    }

    private void initView() {
        dataTest1();
        dataTest2();



    }

    private void dataTest2() {
        //test2:add list data into search result
        //in final version: get the data from backend
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
        for(int i=0;i<15;i++){
            String name="place"+i;
            String description=name+"-"+name+"-"+name+"-"+name+"-"+name+"-"+name+"-"
                    +name+"-"+name+"-"+name+"-"+name+"-"+name+"-"+name+"-"+name+"-"
                    +name+"-"+name+"-"+name+"-"+name+"-"+name+"-" +name+"-"+name+"-"
                    +name+"-"+name+"-"+name+"-"+name;
            Attraction attraction=new Attraction(picsURL.get(i),name,description);
            searchResult.add(attraction);
        }
        RecyclerView recyclerView1=rootView.findViewById(R.id.sr_rv);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView1.setLayoutManager(linearLayoutManager);
        SR_RecyclerViewAdapter sr_recyclerViewAdapter = new SR_RecyclerViewAdapter(searchResult, getContext());
        recyclerView1.setAdapter(sr_recyclerViewAdapter);

        sr_recyclerViewAdapter.setmOnItemClickListener(new SR_RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), AttractionDetailsActivity.class);
                intent.putExtra("attraction", searchResult.get(position));
                startActivity(intent);
            }
        });
    }

    private void dataTest1() {
        //test1: add list data into recent search
        //in final version: get the data from sp(cache)
        for(int i=0;i<15;i++){
            String searchItem="place"+i;
            recentSearch.add(searchItem);
        }

        RecyclerView recyclerView=rootView.findViewById(R.id.rs_rv);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        RS_RecyclerViewAdapter RSRecyclerViewAdapter = new RS_RecyclerViewAdapter(recentSearch, getContext());
        recyclerView.setAdapter(RSRecyclerViewAdapter);
    }


}