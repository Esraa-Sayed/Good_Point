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
        }
    }
}