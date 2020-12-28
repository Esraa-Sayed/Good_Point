package com.helloworld.goodpoint.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.helloworld.goodpoint.R;

public class ActionActivity extends AppCompatActivity {
    Button lost;
    Button found;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lost = (Button) findViewById(R.id.button_lost);
        found = (Button) findViewById(R.id.button_found);

    }
    public void lost_btn(View v){
        Toast.makeText(this, "Action lost", Toast.LENGTH_SHORT).show();
    }
    public void found_btn(View v){
        Toast.makeText(this, "Action found", Toast.LENGTH_SHORT).show();
    }


}