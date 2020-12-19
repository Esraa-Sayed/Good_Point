package com.helloworld.goodpoint.ui;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import com.helloworld.goodpoint.R;

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
        else {
            setContentView(R.layout.activity_main);
            startActivity(new Intent(this,SigninActivity.class));
            finish();
        }
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this,SignupActivity.class));
        finish();
    }
}