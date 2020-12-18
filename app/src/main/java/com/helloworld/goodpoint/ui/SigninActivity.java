package com.helloworld.goodpoint.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.helloworld.goodpoint.R;

import java.util.Calendar;

public class SigninActivity extends AppCompatActivity {
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
        Sigin =findViewById(R.id.sigin);
        CreateNewAccount = findViewById(R.id.NewAccount);
    }
}