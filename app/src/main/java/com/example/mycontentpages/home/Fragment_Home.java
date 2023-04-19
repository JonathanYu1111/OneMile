package com.example.mycontentpages.home;

import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.mycontentpages.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Home extends Fragment {

    View rootView;

    public Fragment_Home() {
        // Required empty public constructor
    }

    public static Fragment_Home newInstance() {
        Fragment_Home fragment = new Fragment_Home();
        return fragment;
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
            rootView = inflater.inflate(R.layout.fragment_home, container, false);
        }
        initView();
        return rootView;
    }

    private void initView() {
        changeView();
        doFilter();
    }

    //implement imageButton function: filter the type and status of attractions
    private void doFilter() {
        ImageButton filter_btn=rootView.findViewById(R.id.filter_btn);
        filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawerLayout = rootView.findViewById(R.id.drawer_layout);
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });
    }
    //implement button function: changeTo ListView or MapView
    private void changeView() {
        Button lmButton = rootView.findViewById(R.id.list_view);
        lmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 找到当前显示的 Fragment
                Fragment currentFragment = getChildFragmentManager().findFragmentById(R.id.map_fragment_container);
                Fragment mapFragment=new MapsFragment();
                Fragment listViewFragment=new ListViewFragment();
                if(currentFragment != null){
                    //进行后续操作
                    // 根据当前 Fragment 判断要切换到哪个 Fragment
                    Fragment targetFragment;
                    String buttonName;
                    if (currentFragment instanceof MapsFragment) {
                        targetFragment = listViewFragment;
                        buttonName = "map_view";
                    } else {
                        targetFragment = mapFragment;
                        buttonName = "list_view";
                    }

                    // 切换 Fragment
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.map_fragment_container, targetFragment)
                            .commit();
                    // 更新 Button 名称
                    lmButton.setText(buttonName);
                }
            }
        });
    }
}