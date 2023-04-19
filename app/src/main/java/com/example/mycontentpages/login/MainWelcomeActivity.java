package com.example.mycontentpages.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mycontentpages.R;

public class MainWelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_welcome);

        Button letsGoButton = findViewById(R.id.lets_go_button);
        letsGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //communicating between two activities
                Intent myInt = new Intent(new Intent(MainWelcomeActivity.this, LoginActivity.class));
                startActivity(myInt);
            }
        });
    }

}