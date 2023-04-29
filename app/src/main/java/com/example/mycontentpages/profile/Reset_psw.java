package com.example.mycontentpages.profile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mycontentpages.MainActivity;
import com.example.mycontentpages.R;
import com.example.mycontentpages.Utils.OkHttp;
import com.example.mycontentpages.Utils.SpUtils;

import org.json.JSONObject;

public class Reset_psw extends AppCompatActivity {

    private Button buttonSave;
    EditText nPsw,cPsw;

    String fail = "Two input password must be consistent!";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_reset_psw);
        buttonSave=findViewById(R.id.buttonSave);
        nPsw=findViewById(R.id.npwd);
        cPsw=findViewById(R.id.cpwd);
        // 获取 ActionBar 对象
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // 在 ActionBar 上显示返回按钮
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nPwd = nPsw.getText().toString();
                String cPwd = cPsw.getText().toString();

                if (nPwd != cPwd){
                    Toast.makeText(getApplicationContext(),fail,Toast.LENGTH_LONG).show();
                    return;
                }

                //send request
                new Thread(()->{
                    try {
                        //send json data convert to json
                        JSONObject json = new JSONObject();
                        json.put("newPassword", nPwd);
                        json.put("confirmPassword", cPwd);
                        String s = OkHttp.sendPostRequest("/member/resetPassword", String.valueOf(json));

                        JSONObject jsonObject = new JSONObject(s);
                        String code = jsonObject.getJSONObject("data").getString("code");
                        String msg = jsonObject.getString("msg");
                        if (Integer.valueOf(code) == 20031){
                            Intent intent = null;
                            intent = new Intent(getApplicationContext(), Fragment_Profile_signed.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).start();

            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}