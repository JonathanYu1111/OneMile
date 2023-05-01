package com.example.mycontentpages.attractionInfo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mycontentpages.R;
import com.example.mycontentpages.Utils.DataContainer;
import com.example.mycontentpages.data.Place;

import java.util.ArrayList;
import java.util.List;

public class AttractionDetailsActivity extends AppCompatActivity {
    List<String> attPicsUrl=new ArrayList<>();
    List<String> commentList=new ArrayList<>();
    RecyclerView comment_rv;
    Place place;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attraction_info_layout);
        String placeID =getIntent().getStringExtra("placeID");
        place= DataContainer.getIDandPlace().get(placeID);
         String name =place.getName();
          //String name =getIntent().getStringExtra("name");

        TextView nameTextView = findViewById(R.id.attr_info_name);

        nameTextView.setText(name);

        initiateView();

        // 获取 ActionBar 对象
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // 在 ActionBar 上显示返回按钮
            actionBar.setDisplayHomeAsUpEnabled(true);
        }



    }

    private void initiateView() {
        if(place !=null){
            getAndSetAttractionData();
        }
        System.out.println(place);

//        ViewPager2 attr_info_vp = findViewById(R.id.attr_info_vp);
//
//        //test1:add random data into url list,
//        //the data should be provided from back end.
////        attPicsUrl.add("http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg");
//        attPicsUrl.add("https://images.unsplash.com/photo-1681649803940-462ad5e90abf?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2274&q=80");
//        attPicsUrl.add("https://images.unsplash.com/photo-1681649803940-462ad5e90abf?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2274&q=80");
//        attPicsUrl.add("https://images.unsplash.com/photo-1681649803940-462ad5e90abf?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2274&q=80");
//        AI_P_Adapter adapter = new AI_P_Adapter(this, attPicsUrl);
//        attr_info_vp.setAdapter(adapter);
//
//        //test2:add data into comment list
//        //the data should be provided from back end.
//        for (int i=0;i<25;i++){
//            String comment="user"+i+":"+"comment content"+i;
//            commentList.add(comment);
//        }
//        comment_rv= findViewById(R.id.attr_info_comment_rv);
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
//        comment_rv.setLayoutManager(linearLayoutManager);
//        AI_C_RecyclerViewAdapter ai_C_recyclerViewAdapter =new AI_C_RecyclerViewAdapter(commentList,this);
//        comment_rv.setAdapter(ai_C_recyclerViewAdapter);

        showReadMore();
        showOpenTime();
        showComment();
        addComment();

    }

    private void getAndSetAttractionData() {
        //attraction=(Attraction) getIntent().getSerializableExtra("attraction");
        attPicsUrl.add(place.getFirstPhoto());
        TextView tv_description=findViewById(R.id.attr_info_description);
        TextView tv_name=findViewById(R.id.attr_info_name);
        tv_name.setText(place.getName());
        tv_description.setText(place.getDescription());
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


}