package com.example.mycontentpages.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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




        //send request
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://8.208.25.129:8090")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        MemberService memberService = retrofit.create(MemberService.class);
//        Call<Member> call = memberService.memberInfo();
//
//        call.enqueue(new Callback<Member>() {
//            @Override
//            public void onResponse(Call<Member> call, Response<Member> response) {
//                // 处理成功响应
//                Member body = response.body();
//
//                Log.e("TAG-ONE", "onResponse: success"+body);
//            }
//
//            @Override
//            public void onFailure(Call<Member> call, Throwable t) {
//                // 处理失败响应
//                Log.e("TAG-ONE", "onResponse: error" + t.getMessage());
//            }
//        });
        try {
            JSONObject json = new JSONObject();
            json.put("username", userName);
            json.put("password", passWord);
            Log.e("TAG-ONE", String.valueOf(json));
            String s = OkHttp.sendPostRequest("http://10.58.196.244:8090/member/loginVerification", String.valueOf(json));
            Log.e("TAG-ONE", s);
        } catch (Exception e) {
            Log.i("post","post fail");
            Log.e("TAG-ONE"," "+ e.getMessage());
        }

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
