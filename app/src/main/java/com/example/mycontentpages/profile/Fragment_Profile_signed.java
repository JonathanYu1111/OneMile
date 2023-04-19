package com.example.mycontentpages.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.mycontentpages.R;
import com.example.mycontentpages.home.Fragment_Home;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Profile_signed extends Fragment {

    View rootView;

    public Fragment_Profile_signed() {
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
            rootView = inflater.inflate(R.layout.fragment_profile_signed, container, false);
        }
        initView();
        return rootView;
    }

    private void initView() {
        rootView.findViewById(R.id.user_name);
    }
}