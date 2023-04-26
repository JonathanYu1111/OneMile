package com.example.mycontentpages.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mycontentpages.R;
import com.example.mycontentpages.login.LoginActivity;
import com.example.mycontentpages.profile.About;

public class Account_guestUser extends Fragment {

    //声明控件
    private Button guestSignIn;
    private Button guestSignUp;
    private TextView about_tv;

    public Account_guestUser() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_account_guest_user, container, false);

        //找到控件
        guestSignIn = rootView.findViewById(R.id.signIn);
        guestSignUp = rootView.findViewById(R.id.signUp);
        about_tv = rootView.findViewById(R.id.about);

        guestSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        guestSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        about_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), About.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
