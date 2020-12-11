package com.helloworld.goodpoint.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.helloworld.goodpoint.R;

import java.util.Calendar;

import com.helloworld.goodpoint.R;

import com.helloworld.goodpoint.R;

public class SignupActivity extends AppCompatActivity {
    private TextView DateT ;
    private DatePickerDialog.OnDateSetListener DateSet;
    private int year, month, Day;

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
                if (y > year ) {
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

    }

    protected void inti() {
        DateT = findViewById(R.id.Date);
        Calendar cal = Calendar.getInstance();//To get today's date
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        Day = cal.get(Calendar.DAY_OF_MONTH);
        String TodayDate =  Day+ "/" + (month + 1) + "/" + year;
        DateT.setText(TodayDate);

    }
}