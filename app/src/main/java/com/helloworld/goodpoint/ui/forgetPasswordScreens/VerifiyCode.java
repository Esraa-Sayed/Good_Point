package com.helloworld.goodpoint.ui.forgetPasswordScreens;

import androidx.appcompat.app.AppCompatActivity;
import com.helloworld.goodpoint.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class VerifiyCode extends AppCompatActivity {
private Button verify_btn;
private ImageView back_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifiy_code);
        verify_btn=(Button)findViewById(R.id.verify_btn);
        back_btn=(ImageView)findViewById(R.id.back_btn);

        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VerifiyCode.this, SetNewPassword.class));
                finish();
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