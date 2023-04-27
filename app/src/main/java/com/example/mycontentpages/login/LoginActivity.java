package com.example.mycontentpages.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
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
        new Thread(()->{
            try {
                //send json data convert to json
                JSONObject json = new JSONObject();
                json.put("username", userName);
                json.put("password", passWord);
                String s = OkHttp.sendPostRequest("http://10.58.198.132:8090/member/loginVerification", String.valueOf(json));
                Log.e("Tag-one", s);
                //获取返回数据在页面上进行更新
                runOnUiThread(()->{

                });
            } catch (Exception e) {
                Toast.makeText(this,"Network connection failure",Toast.LENGTH_LONG).show();
                throw new RuntimeException(e);
            }
        }).start();

        if (userName.equals("abc") && passWord.equals("abc")) {
            Toast.makeText(this,success,Toast.LENGTH_LONG).show();
            intent = new Intent(this, MainActivity.class);
            intent.putExtra("login_success", true);
            setResult(Activity.RESULT_OK, intent);
            finish();
            startActivity(intent);
        }else{
            Toast.makeText(this,fail,Toast.LENGTH_LONG).show();
        }
   }



}
