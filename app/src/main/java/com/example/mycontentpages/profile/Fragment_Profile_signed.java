package com.example.mycontentpages.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mycontentpages.R;
import com.example.mycontentpages.attractionInfo.AttractionDetailsActivity;
import com.example.mycontentpages.home.Fragment_Home;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Profile_signed extends Fragment {

    View rootView;
    private TextView about_tv,resetPsw;

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
        about_tv=rootView.findViewById(R.id.about);
        resetPsw=rootView.findViewById(R.id.resetPsw);
        about_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), About.class);
                startActivity(intent);
            }
        });
        resetPsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), Reset_psw.class);
                startActivity(intent);
            }
        });
    }
}