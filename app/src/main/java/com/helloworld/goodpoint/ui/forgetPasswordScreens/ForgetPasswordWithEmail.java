package com.helloworld.goodpoint.ui.forgetPasswordScreens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.helloworld.goodpoint.R;

public class ForgetPasswordWithEmail extends AppCompatActivity {
    private Button next_btn;
    private ImageView back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        next_btn = (Button) findViewById(R.id.Next_btn);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgetPasswordWithEmail.this, VerifiyCode.class));
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}