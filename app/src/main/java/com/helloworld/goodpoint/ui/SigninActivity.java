package com.helloworld.goodpoint.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.helloworld.goodpoint.R;

import java.util.Calendar;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText Email,Pass;
    private TextView ForgetPass;
    private CheckBox RememberMe;
    private Button Sigin ,CreateNewAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        inti();
    }

    protected void inti() {
        Email = findViewById(R.id.email);
        Pass =findViewById(R.id.pass);
        ForgetPass = findViewById(R.id.forgetPass);
        Sigin =findViewById(R.id.signin);
        CreateNewAccount = findViewById(R.id.NewAccount);

        Sigin.setOnClickListener(this);
        CreateNewAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signin:
                if(validAccount()) {
                    startActivity(new Intent(this, HomeActivity.class));
                    finish();
                }else
                    Toast.makeText(this, "Invalid account", Toast.LENGTH_SHORT).show();
                break;
            case R.id.NewAccount:
                    startActivity(new Intent(this, SignupActivity.class));
                    break;
        }
    }

    private boolean validAccount() {
        //check validation
        return true;
    }
}