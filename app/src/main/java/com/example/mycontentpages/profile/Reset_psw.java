package com.example.mycontentpages.profile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.example.mycontentpages.R;

public class Reset_psw extends AppCompatActivity {

    private Button buttonSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_reset_psw);
        buttonSave=findViewById(R.id.buttonSave);
        // 获取 ActionBar 对象
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // 在 ActionBar 上显示返回按钮
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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