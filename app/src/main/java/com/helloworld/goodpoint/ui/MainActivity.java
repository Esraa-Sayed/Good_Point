package com.helloworld.goodpoint.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.helloworld.goodpoint.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void IntentB(View v)
    {
        Toast.makeText(this, "HI", Toast.LENGTH_SHORT).show();
        Intent I  = new Intent(this , LostObjectDetailsActivity.class);
        startActivity(I);
    }
}