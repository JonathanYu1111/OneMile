package com.example.mycontentpages.attractionInfo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycontentpages.MainActivity;
import com.example.mycontentpages.R;
import com.example.mycontentpages.Utils.OkHttp;
import com.example.mycontentpages.Utils.SpUtils;
import com.example.mycontentpages.data.Place;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.mycontentpages.Utils.DataContainer;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AttractionDetailsActivity extends AppCompatActivity {
    List<String> attPicsUrl=new ArrayList<>();
    List<String> commentList=new ArrayList<>();

    private ImageView collectionButton;
    int collectionFlag = 0;
    RecyclerView comment_rv;

    String token;

    String placeID;
    Place place;

    private GoogleMap theMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attraction_info_layout);

         placeID =getIntent().getStringExtra("placeID");
        place= DataContainer.getIDandPlace().get(placeID);
         String name =place.getName();
          //String name =getIntent().getStringExtra("name");

        collectionButton = findViewById(R.id.collectionButton);

        TextView nameTextView = findViewById(R.id.attr_info_name);
        nameTextView.setText(name);

        token = SpUtils.getString(MainActivity.getContext(), "token");

        initiateView();
        // 获取 ActionBar 对象
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // 在 ActionBar 上显示返回按钮
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        MapFragment mapFragment = MapFragment.newInstance();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.map_small, mapFragment);
        transaction.commit();

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap map) {
                theMap=map;
                LatLng placeLatLng=new LatLng(place.getLatitude(),place.getLongitude());
                theMap.moveCamera(CameraUpdateFactory.newLatLngZoom(placeLatLng, 15));
                MarkerOptions markerOptions=new MarkerOptions().position(placeLatLng).title(place.getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                Marker marker =theMap.addMarker(markerOptions);

            }
        });
    }

    private void getCollectStatus() {

        //send request
        new Thread(()->{
            try {
                //send json data convert to json
                JSONObject json = new JSONObject();
                json.put("place_id", placeID);
                String s = OkHttp.sendPostRequest("/collect/info", String.valueOf(json));
                JSONObject jsonObject = new JSONObject(s);
                Integer status = Integer.parseInt(jsonObject.getString("data"));
                String code = jsonObject.getString("code");
                if (Integer.valueOf(code) != 20041){
                    Looper.prepare(); // 创建一个 Looper 对象
                    Toast.makeText(this, "Sorry, can not get the collection status",Toast.LENGTH_LONG).show();
                    Looper.loop(); // 开始消息循环
                    return;
                }
                collectionFlag = status;
                intentCollectionState(collectionButton);

            } catch (Exception e) {
                Log.e("one", e.getMessage());
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void initiateView() {
        if(place !=null){
            getAndSetAttractionData();
        }
        System.out.println(place);

        ViewPager2 attr_info_vp = findViewById(R.id.attr_info_vp);
        //test1:add random data into url list,
        //the data should be provided from back end.
        attPicsUrl.add("https://images.unsplash.com/photo-1681649803940-462ad5e90abf?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2274&q=80");
        attPicsUrl.add("https://images.unsplash.com/photo-1681649803940-462ad5e90abf?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2274&q=80");
        attPicsUrl.add("https://images.unsplash.com/photo-1681649803940-462ad5e90abf?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2274&q=80");
        AI_P_Adapter adapter = new AI_P_Adapter(this, attPicsUrl);
        attr_info_vp.setAdapter(adapter);

        if (token != ""){
            getCollectStatus();
        }

        //test2:add data into comment list
        //the data should be provided from back end.
        for (int i=0;i<25;i++){
            String comment="user"+i+":"+"comment content"+i;
            commentList.add(comment);
        }
        comment_rv= findViewById(R.id.attr_info_comment_rv);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        comment_rv.setLayoutManager(linearLayoutManager);
        AI_C_RecyclerViewAdapter ai_C_recyclerViewAdapter =new AI_C_RecyclerViewAdapter(commentList,this);
        comment_rv.setAdapter(ai_C_recyclerViewAdapter);
        showReadMore();
        showOpenTime();
        showComment();
        addComment();

    }

    private void getAndSetAttractionData() {
        //attraction=(Attraction) getIntent().getSerializableExtra("attraction");
        attPicsUrl.add(place.getFirstPhoto());
        TextView tv_description=findViewById(R.id.attr_info_description);
        tv_description.setText(place.getFullAddress());
        TextView tv_name=findViewById(R.id.attr_info_name);
        tv_name.setText(place.getName());

    }

    private void addComment() {
        Button addComment_btn=findViewById(R.id.attr_info_addComment_btn);
        addComment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View dialogView=getLayoutInflater().inflate(R.layout.attraction_info_addcomment_pop,null);

                AlertDialog.Builder builder=new AlertDialog.Builder(AttractionDetailsActivity.this);
                builder.setView(dialogView)
                        .setPositiveButton("commit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .create()
                        .show();
            }
        });
    }

    private void showComment() {
        ImageView attnInfo_show_comments = findViewById(R.id.attnInfo_show_comments);

        attnInfo_show_comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(comment_rv.getVisibility()==View.GONE){
                    comment_rv.setVisibility(View.VISIBLE);
                    attnInfo_show_comments.setImageResource(R.drawable.baseline_keyboard_arrow_up_24);
                }else{
                    comment_rv.setVisibility(View.GONE);
                    attnInfo_show_comments.setImageResource(R.drawable.baseline_keyboard_arrow_down_24);
                }
            }
        });
    }

    private void showOpenTime() {
        ImageView attnInfo_show_openTime = findViewById(R.id.attnInfo_show_openTime);
        TextView openTimeText = findViewById(R.id.openTime_text);
        attnInfo_show_openTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (openTimeText.getVisibility() == View.GONE) {
                    openTimeText.setVisibility(View.VISIBLE);
                    attnInfo_show_openTime.setImageResource(R.drawable.baseline_keyboard_arrow_up_24);
                } else {
                    openTimeText.setVisibility(View.GONE);
                    attnInfo_show_openTime.setImageResource(R.drawable.baseline_keyboard_arrow_down_24);
                }
            }
        });
    }

    private void showReadMore(){
        TextView tvContent = findViewById(R.id.attr_info_description);
        TextView tvReadMore = findViewById(R.id.tv_read_more);
        tvReadMore.setOnClickListener(new View.OnClickListener() {
            Boolean isExpanded=false;
            @Override
            public void onClick(View v) {
                if (isExpanded) {
                    // 如果文本已经展开，则收起文本，并将按钮文本设置为 "Read more"
                    tvContent.setMaxLines(3);
                    tvReadMore.setText("Read more");
                    isExpanded = false;
                } else {
                    // 如果文本没有展开，则展开文本，并将按钮文本设置为 "Hide"
                    tvContent.setMaxLines(Integer.MAX_VALUE);
                    tvReadMore.setText("Hide");
                    isExpanded = true;
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void intentCollectionState(View view){
        ImageView imageView = (ImageView) view;
        if (collectionFlag == 0) {
            imageView.setImageResource(R.drawable.favorites_normal);

        } else {
            imageView.setImageResource(R.drawable.favorites_pressed);
        }
    }
    public void pressCollectionButton(View view){

        new Thread(()->{
            try {
                //send json data convert to json
                JSONObject json = new JSONObject();
                json.put("place_id", placeID);
                json.put("status", collectionFlag);
                String s = OkHttp.sendPostRequest("/collect/change", String.valueOf(json));
                JSONObject jsonObject = new JSONObject(s);
                Integer status = Integer.parseInt(jsonObject.getString("data"));
                String code = jsonObject.getString("code");
                if (Integer.valueOf(code) != 20031){
                    Looper.prepare(); // 创建一个 Looper 对象
                    Toast.makeText(this, "Sorry, can not change the collection status",Toast.LENGTH_LONG).show();
                    Looper.loop(); // 开始消息循环
                    return;
                }
                collectionFlag = status;
                intentCollectionState(collectionButton);


            } catch (Exception e) {
                Log.e("one", "6666");
                Log.e("one", e.getMessage());
                throw new RuntimeException(e);
            }
        }).start();
    }

}