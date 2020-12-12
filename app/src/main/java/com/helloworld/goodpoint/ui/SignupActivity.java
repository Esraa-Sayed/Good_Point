package com.helloworld.goodpoint.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.helloworld.goodpoint.R;

import java.io.IOException;
import java.util.Calendar;
import java.util.regex.Pattern;

import com.helloworld.goodpoint.R;
import android.util.Patterns;

public class SignupActivity extends AppCompatActivity {
    private TextView DateT;
    private EditText UserName, Email, Password, Phone, Country;
    private DatePickerDialog.OnDateSetListener DateSet;
    private int year, month, Day;
    private ImageView image;
    Button CreateAccount;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        inti();
        DateT.setOnClickListener(new View.OnClickListener() {
            // @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(
                        SignupActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        DateSet,
                        year, month, Day
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }

        });
        DateSet = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                m++;
                if (y > year) {
                    Toast.makeText(SignupActivity.this, "", Toast.LENGTH_SHORT).show();
                    String todayDate = year + "/" + (month + 1) + "/" + Day;
                    DateT.setText(todayDate);
                } else {
                    Toast.makeText(SignupActivity.this, "hi", Toast.LENGTH_SHORT).show();
                    String Date = d + "/" + m + "/" + y;
                    DateT.setText(Date);
                }
            }
        };
    CreateAccount.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    });
    }

    protected void inti() {
        UserName = findViewById(R.id.Name);
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Pass);
        Country = findViewById(R.id.country);
        DateT = findViewById(R.id.Date);
        image = findViewById(R.id.im);
        CreateAccount = findViewById(R.id.createAccount);
        Calendar cal = Calendar.getInstance();//To get today's date
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        Day = cal.get(Calendar.DAY_OF_MONTH);
        String TodayDate = Day + "/" + (month + 1) + "/" + year;
        DateT.setText(TodayDate);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Toast.makeText(this, "onCreateContextMenu", Toast.LENGTH_SHORT);
        //menu.getItem(2).setEnabled(false);
        menu.findItem(R.id.Delete).setVisible(false);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_capture:
                //Toast.makeText(this,"Hello!!",Toast.LENGTH_SHORT).show();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(cameraIntent, 10);
                }
                break;
            case R.id.action_choose:
                //Toast.makeText(this,"Hello",Toast.LENGTH_SHORT).show();
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, 11);
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    private boolean validateEmail() {
        String emailInput = Email.getText().toString().trim();
        if (emailInput.isEmpty()) {
            Email.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            Email.setError("Please enter a valid email address");
            return false;
        } else {
            Email.setError(null);
            return true;
        }
    }
    private boolean validateUsername() {
        String usernameInput = UserName.getText().toString().trim();
        if (usernameInput.isEmpty()) {
            UserName.setError("Field can't be empty");
            return false;
        } else if (usernameInput.length() > 15) {
            UserName.setError("Username too long");
            return false;
        } else {
            UserName.setError(null);
            return true;
        }
    }
    private boolean validatePassword() {
        String passwordInput = Password.getText().toString().trim();
        if (passwordInput.isEmpty()) {
            Password.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            Password.setError("Password too weak");
            return false;
        } else {
            Password.setError(null);
            return true;
        }
    }
    public void confirmInput(View v) {
        if (!validateEmail() | !validateUsername() | !validatePassword()) {
            return;
        }
        String input = "Email: " + Email.getText().toString();
        input += "\n";
        input += "Username: " + UserName.getText().toString();
        input += "\n";
        input += "Password: " + Password.getText().toString();
        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
    }
}