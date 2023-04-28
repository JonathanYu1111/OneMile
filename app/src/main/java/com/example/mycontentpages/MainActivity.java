package com.example.mycontentpages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mycontentpages.Utils.DataContainer;
import com.example.mycontentpages.Utils.OkHttp;
import com.example.mycontentpages.Utils.SpUtils;
import com.example.mycontentpages.account.Account_guestUser;
import com.example.mycontentpages.favorites.Fragment_Favorites;
import com.example.mycontentpages.home.Fragment_Home;
import com.example.mycontentpages.login.*;
import com.example.mycontentpages.profile.Fragment_Profile_signed;
import com.example.mycontentpages.search.Fragment_Search;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    LoginActivity l = new LoginActivity();

    int i=1;

    Fragment_Home bf1=new Fragment_Home();
    Fragment_Search bf2=new Fragment_Search();
    Fragment_Favorites bf3=new Fragment_Favorites();
    Fragment_Profile_signed bf4=new Fragment_Profile_signed();

    Account_guestUser gf=new Account_guestUser();
    //判断登录状态,i在哪里开始调用
    public  static Thread initThread;

    // static context
    public static Context context;

    // get context
    public static Context getContext() {
        return context;
    }


    private LinearLayout llHome,llSearch,llFavorites,llProfile;
    private ImageView ivHome,ivSearch,ivFavorites,ivProfile,ivCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //初始化 context
        context = getApplicationContext();

        initThread = new Thread(()->{
            try {
                DataContainer.initialize();
            } catch (IOException e) {
                Log.i("initialize","initialize failed");
            }
        });
        initThread.start();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getBoolean("login_success", false)) {
            i = 1;
        }

        System.out.println(i);
        //初始化viewpager
        initPager();
        //初始化UI
        initTabView();


    }

    private void initTabView() {
        llHome=findViewById(R.id.id_tag_home);
        llHome.setOnClickListener(this);
        llSearch=findViewById(R.id.id_tag_search);
        llSearch.setOnClickListener(this);
        llFavorites=findViewById(R.id.id_tag_favorites);
        llFavorites.setOnClickListener(this);
        llProfile=findViewById(R.id.id_tag_profile);
        llProfile.setOnClickListener(this);
        ivHome=findViewById(R.id.tab_iv_home);
        ivSearch=findViewById(R.id.tab_iv_search);
        ivFavorites=findViewById(R.id.tab_iv_favorites);
        ivProfile=findViewById(R.id.tab_iv_profile);

        ivHome.setSelected(true);
        ivCurrent=ivHome;
    }


    private void initPager() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,bf1)
                .commit();
    }

    private void changeTab(int position) {

        Intent intent=new Intent(this, Account_guestUser.class);

        ivCurrent.setSelected(false);


        switch (position){
            case R.id.id_tag_home:
                //viewPager.setCurrentItem(0);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, bf1)
                        .commit();
                ivHome.setSelected(true);
                ivCurrent=ivHome;
                break;
            case R.id.id_tag_search:
                //viewPager.setCurrentItem(1);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, bf2)
                        .commit();
                ivSearch.setSelected(true);
                ivCurrent=ivSearch;
                break;
            case R.id.id_tag_favorites:
                //fake data:test page
                if(i == 1){
                    //viewPager.setCurrentItem(2);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, bf3)
                            .commit();
                    ivFavorites.setSelected(true);
                    ivCurrent=ivFavorites;
                }else{
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, gf)
                            .commit();
                    ivFavorites.setSelected(true);
                    ivCurrent=ivFavorites;
                }

                break;
            case R.id.id_tag_profile:
                //fake data:test page
                if(i == 1){
                    //viewPager.setCurrentItem(3);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, bf4)
                            .commit();
                    ivProfile.setSelected(true);
                    ivCurrent=ivProfile;
                }else{
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, gf)
                            .commit();
                    ivProfile.setSelected(true);
                    ivCurrent=ivProfile;
                }

                break;
        }

    }

    @Override
    public void onClick(View view) {
        changeTab(view.getId());
    }
}