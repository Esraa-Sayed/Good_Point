package com.helloworld.goodpoint.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.helloworld.goodpoint.R;
import com.helloworld.goodpoint.ui.lostObject.LostObjectDetailsActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
Button Found , Lost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Found = findViewById(R.id.Found);
        Lost = findViewById(R.id.Lost);
        Found.setOnClickListener(this);
        Lost.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == Found)
        {
            Intent I  = new Intent(this , FoundObjectActivity.class);
            startActivity(I);
        }
        else if (view == Lost)
        {
            Intent I  = new Intent(this , LostObjectDetailsActivity.class);
            startActivity(I);
        }
    }
}