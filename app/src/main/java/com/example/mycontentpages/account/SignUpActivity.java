package com.example.mycontentpages.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycontentpages.MainActivity;
import com.example.mycontentpages.R;
import com.example.mycontentpages.Utils.OkHttp;
import com.example.mycontentpages.Utils.SpUtils;

import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {
    TextView signUp;
    ImageView closeButton;
    EditText userName,password,c_password;

    String hint="Two input passwords must be consistent!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        closeSignUpPage();
        signUp();

    }

    // This method is to close the sign up page and return back to the sign up activity page
    public void closeSignUpPage(){
        closeButton = findViewById(R.id.close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //This method is for guest users to sign up when clicking on the signUp button.
    public void signUp(){
        signUp=findViewById(R.id.signUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName=findViewById(R.id.user_name);
                password=findViewById(R.id.password);
                c_password=findViewById(R.id.c_password);
                String username=userName.getText().toString();
                String pwd=password.getText().toString();
                String c_pwd=c_password.getText().toString();
                if(!pwd.equals(c_pwd)){
                    Toast.makeText(getApplicationContext(),hint,Toast.LENGTH_LONG).show();
                }else{
                    //send request
                    new Thread(()->{
                        try {
                            //send json data convert to json
                            JSONObject json = new JSONObject();
                            json.put("username", username);
                            json.put("password", pwd);
                            json.put("confirmPassword", c_pwd);
                            String s = OkHttp.sendPostRequest("/member/register", String.valueOf(json));

                            JSONObject jsonObject = new JSONObject(s);

                            String code = jsonObject.getString("code");
                            String msg = jsonObject.getString("msg");
                            if (Integer.valueOf(code) != 20031){
                                Looper.prepare(); // 创建一个 Looper 对象
                                Log.e("one", msg);
                                Toast.makeText(getApplicationContext(),"register fail, please check!" + msg,Toast.LENGTH_LONG).show();
                                Looper.loop(); // 开始消息循环
                                return;
                            }
                            String token = jsonObject.getJSONObject("data").getString("token");
                            // store the token
                            SpUtils.putString(MainActivity.getContext(), "token", token);

                            Intent intent = null;
                            intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }).start();
                }


            }
   });
}
}
