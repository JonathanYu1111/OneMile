package com.example.mycontentpages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.mycontentpages.account.Account_guestUser;
import com.example.mycontentpages.favorites.Fragment_Favorites;
import com.example.mycontentpages.home.Fragment_Home;
import com.example.mycontentpages.profile.Fragment_Profile_signed;
import com.example.mycontentpages.search.Fragment_Search;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //fragment依托于viewpager,viewpager显示在Activity的layout上
    ViewPager2 viewPager;
    int i=1;

    private LinearLayout llHome,llSearch,llFavorites,llProfile;
    private ImageView ivHome,ivSearch,ivFavorites,ivProfile,ivCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        viewPager=findViewById(R.id.vp1);
        ArrayList<Fragment> fragments=new ArrayList<>();
        Fragment_Home bf1=new Fragment_Home();
        Fragment_Search bf2=new Fragment_Search();
        Fragment_Favorites bf3=new Fragment_Favorites();
        Fragment_Profile_signed bf4=new Fragment_Profile_signed();
        fragments.add(bf1);
        fragments.add(bf2);
        fragments.add(bf3);
        fragments.add(bf4);
        MyFragmentPagerAdapter pagerAdapter=new MyFragmentPagerAdapter(getSupportFragmentManager(),
                getLifecycle(),fragments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                changeTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    private void changeTab(int position) {

        Intent intent=new Intent(this, Account_guestUser.class);

        ivCurrent.setSelected(false);
        switch (position){
            case R.id.id_tag_home:
                viewPager.setCurrentItem(0);
            case 0:
                ivHome.setSelected(true);
                ivCurrent=ivHome;
                break;
            case R.id.id_tag_search:
                viewPager.setCurrentItem(1);
            case 1:
                ivSearch.setSelected(true);
                ivCurrent=ivSearch;
                break;
            case R.id.id_tag_favorites:
                //fake data:test page
                if(i==1){
                    viewPager.setCurrentItem(2);
                }else{
                    startActivity(intent);
                }
            case 2:
                if(i==1){
                    ivFavorites.setSelected(true);
                    ivCurrent=ivFavorites;
                }
                break;
            case R.id.id_tag_profile:
                //fake data:test page
                if(i==1){
                    viewPager.setCurrentItem(3);
                }else{
                    startActivity(intent);
                }
            case 3:
                if(i==1){
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