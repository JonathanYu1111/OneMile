package com.example.mycontentpages.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mycontentpages.MainActivity;
import com.example.mycontentpages.R;
import com.example.mycontentpages.login.LoginActivity;

public class Account_guestUser extends AppCompatActivity {

    //声明控件
    private Button guestSignIn;
    private Button guestSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_guest_user);

        //找到控件
        guestSignIn = findViewById(R.id.signIn);
        guestSignUp = findViewById(R.id.signUp);
        guestSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(Account_guestUser.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        guestSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(Account_guestUser.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}