package com.example.mycontentpages.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycontentpages.MainActivity;
import com.example.mycontentpages.R;
import com.example.mycontentpages.account.Account_guestUser;
import com.example.mycontentpages.account.SignUpActivity;

public class LoginActivity extends AppCompatActivity {


    private Button signIn;
    private Button signUp;
    private EditText username;
    private EditText password;
    //用于判断登录状态
    public int flag = 0;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signIn = findViewById(R.id.signIn);
        signUp = findViewById(R.id.signUp);
        username = findViewById(R.id.signin_username);
        password = findViewById(R.id.signin_password);

        signIn.setOnClickListener(this::onClick);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onClick(View view) {
        String userName = username.getText().toString();
        String passWord = password.getText().toString();
        String success = "Sign in success!";
        String fail = "Incorrect username or password";
        Intent intent = null;

        if (userName.equals("abc") && passWord.equals("abc")) {
            Toast.makeText(this,success,Toast.LENGTH_LONG).show();


            intent = new Intent(this, MainActivity.class);
            flag = 1;
            startActivity(intent);
        }else{
            Toast.makeText(this,fail,Toast.LENGTH_LONG).show();
        }
   }


}
