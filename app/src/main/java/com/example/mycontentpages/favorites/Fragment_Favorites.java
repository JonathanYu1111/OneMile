package com.example.mycontentpages.favorites;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.example.mycontentpages.R;
import com.example.mycontentpages.Utils.DataContainer;
import com.example.mycontentpages.Utils.OkHttp;
import com.example.mycontentpages.data.Place;
import com.example.mycontentpages.attractionInfo.AttractionDetailsActivity;
import com.example.mycontentpages.home.Fragment_Home;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Favorites extends Fragment implements RecyclerViewInterface{

    View rootView;
    Favorite_RecyclerViewAdapter favorite_recyclerViewAdapter;
    List<Place> favoritePlace =new ArrayList<>();

 //   RecyclerView recyclerView = rootView.findViewById(R.id.favorites_rv);
   // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

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

        favoritePlace.clear();


        filter();

        initView();
        return rootView;
    }

    private void initView() {
       RecyclerView recyclerView = rootView.findViewById(R.id.favorites_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        favorite_recyclerViewAdapter = new Favorite_RecyclerViewAdapter(favoritePlace, getContext(), this);
        //recyclerView.setAdapter(favorite_recyclerViewAdapter);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), AttractionDetailsActivity.class);
        intent.putExtra("name", favoritePlace.get(position).getName());
        intent.putExtra("description", favoritePlace.get(position).getDescription());
        intent.putExtra("picUrl", favoritePlace.get(position).getFirstPhoto());
        intent.putExtra("placeID",favoritePlace.get(position).getGooglePlaceId());

        startActivity(intent);
    }

    private void filter(){

        //send request
        new Thread(()->{
            try {
                //send json data convert to json
                String s = OkHttp.sendPostRequest("/collect/myCollect", "");
                JSONObject jsonObject = new JSONObject(s);

//                Log.e("one", jsonObject.getJSONObject("data") + "");
                String code = jsonObject.getString("code");
                if (Integer.valueOf(code) != 20041){
                    Looper.prepare(); // 创建一个 Looper 对象
                    Toast.makeText(getActivity(), "Sorry, can not get the collection list",Toast.LENGTH_LONG).show();
                    Looper.loop(); // 开始消息循环
                    return;
                }

                String placeArr = jsonObject.getString("data");
                List<Place> places = JSON.parseArray(placeArr, Place.class);
                for (Place place:places) {
                    Log.e("qq", "filter: " + place.toString());
                    favoritePlace.add(place);
                }

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(()->{
                    RecyclerView recyclerView = rootView.findViewById(R.id.favorites_rv);
                    //favorite_recyclerViewAdapter = new Favorite_RecyclerViewAdapter(favoritePlace, getContext(), this);
                    recyclerView.setAdapter(favorite_recyclerViewAdapter);
                });
                Log.e("qq", "filter: " + favoritePlace.toString());



            } catch (Exception e) {
                Log.e("one", e.getMessage());
                throw new RuntimeException(e);
            }
        }).start();
    }
}