package com.example.mycontentpages.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mycontentpages.R;
import com.example.mycontentpages.Utils.BufferData;
import com.example.mycontentpages.Utils.DataContainer;
import com.example.mycontentpages.data.Place;
import com.example.mycontentpages.attractionInfo.AttractionDetailsActivity;

import java.util.ArrayList;
import java.util.List;


public class ListViewFragment extends Fragment {
    View rootView;
    List<Place> places =new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(rootView==null){
            rootView=inflater.inflate(R.layout.fragment_list_view, container, false);
        }

        initData();

        RecyclerView list_view_rv=rootView.findViewById(R.id.list_view_rv);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        list_view_rv.setLayoutManager(linearLayoutManager);
        LW_RecyclerAdapter lw_recyclerAdapter = new LW_RecyclerAdapter(places, getContext());
        list_view_rv.setAdapter(lw_recyclerAdapter);
        lw_recyclerAdapter.setOnRV_itemImageClickListener(new LW_RecyclerAdapter.OnRV_ItemImageClickListener() {
            @Override
            public void onImageClick(int position) {
                Intent intent = new Intent(getActivity(), AttractionDetailsActivity.class);
                intent.putExtra("attraction", places.get(position));
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void initData() {
        //test1: add random data
        //data should be provided from backend
//        List<String> picsURL=new ArrayList<>();//just for test data
//        for(int i=0;i<30;i++){
//            if(i%3==0){
//                picsURL.add("https://images.unsplash.com/photo-1680095297939-5f69d7f139e9?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDF8YkRvNDhjVWh3bll8fGVufDB8fHx8&auto=format&fit=crop&w=600&q=60");
//            }else if(i%3==1){
//                picsURL.add("https://images.unsplash.com/photo-1681312407157-19ec16888a6f?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDEzfGJEbzQ4Y1Vod25ZfHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=600&q=60");
//            }else {
//                picsURL.add("https://images.unsplash.com/photo-1675111575738-80a06bbdb643?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDI1fGJEbzQ4Y1Vod25ZfHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=600&q=60");
//            }
//        }
//        for(int i=0;i<7;i++){
//            String name="place"+i;
//            String description="placeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee+" +i+
//                    "\n+placeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee";
//            Place place =new Place(picsURL.get(i),name,description);
//            places.add(place);
        List<Place> inRangePlaces=new ArrayList<>();
        for(Place place: DataContainer.getPlaceContainer()) {
            for (String googleID : BufferData.getInRangeIDs()) {
                    if(place.getGooglePlaceId().equals(googleID)){
                        inRangePlaces.add(place);
                    }
            }
        }
        places=inRangePlaces;
    }


}