package com.example.mycontentpages.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycontentpages.MainActivity;
import com.example.mycontentpages.R;
import com.example.mycontentpages.Utils.OkHttp;
import com.example.mycontentpages.Utils.SpUtils;
import com.example.mycontentpages.account.Account_guestUser;
import com.example.mycontentpages.account.SignUpActivity;
import com.example.mycontentpages.domain.Member;
import com.example.mycontentpages.service.MemberService;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {


    private Button signIn;
    private Button signUp;
    private EditText username;
    private EditText password;
    //用于判断登录状态
    public int flag = 1;



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




        //send request
        new Thread(()->{
            try {
                //send json data convert to json
                JSONObject json = new JSONObject();
                json.put("username", userName);
                json.put("password", passWord);
                String s = OkHttp.sendPostRequest("/member/loginVerification", String.valueOf(json));

                JSONObject jsonObject = new JSONObject(s);
                String token = jsonObject.getJSONObject("data").getString("token");
                String code = jsonObject.getString("code");
                String msg = jsonObject.getString("msg");
                if (Integer.valueOf(code) != 20041){
                    Looper.prepare(); // 创建一个 Looper 对象
                    Toast.makeText(this,fail,Toast.LENGTH_LONG).show();
                    Looper.loop(); // 开始消息循环
                    return;
                }
                // store the token
                SpUtils.putString(MainActivity.getContext(), "token", token);

                Intent intent = null;
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
   }



}
