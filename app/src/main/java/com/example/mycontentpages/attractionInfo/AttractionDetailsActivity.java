package com.example.mycontentpages.attractionInfo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

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

    String placeID;
    Place place;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attraction_info_layout);

        collectionButton = findViewById(R.id.collectionButton);


        String name =getIntent().getStringExtra("name");
        placeID = getIntent().getStringExtra("placeID");

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

        getCollectStatus();

        showReadMore();
        showOpenTime();
        showComment();
        addComment();

    }

    private void getAndSetAttractionData() {
        Bundle bundle = getIntent().getExtras();
        place = (Place) bundle.getSerializable("attraction");
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