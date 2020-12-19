package com.helloworld.goodpoint.ui.forgetPasswordScreens;

import androidx.appcompat.app.AppCompatActivity;

import com.helloworld.goodpoint.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class SetNewPassword extends AppCompatActivity {

    private Button ok_btn;
    private ImageView back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);
        ok_btn = (Button) findViewById(R.id.ok_btn);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SetNewPassword.this, ForgetPassSuccessMessage.class));
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