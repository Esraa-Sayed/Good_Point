package com.helloworld.goodpoint.ui;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.helloworld.goodpoint.R;
import com.helloworld.goodpoint.ui.forgetPasswordScreens.MakeSelection;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PrefManager prefManager = new PrefManager(getApplicationContext());
        if (prefManager.isFirstTimeLaunch()) {
            prefManager.setFirstTimeLaunch(true);
            startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
            finish();
        } else {
            setContentView(R.layout.activity_main);
            findViewById(R.id.btn_play_again).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PrefManager prefManager = new PrefManager(getApplicationContext());
                    prefManager.setFirstTimeLaunch(true);
                    startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
                    finish();
                }
            });
            findViewById(R.id.forget).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, MakeSelection.class));

                }
            });
        }
    }
}