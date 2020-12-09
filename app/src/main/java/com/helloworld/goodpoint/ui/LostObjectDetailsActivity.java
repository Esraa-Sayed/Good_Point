package com.helloworld.goodpoint.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.helloworld.goodpoint.R;

import java.util.Calendar;

public class LostObjectDetailsActivity extends AppCompatActivity {
    private TextView DateT ;
    private DatePickerDialog.OnDateSetListener DateSet;
    int year , month , Day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_object_details);
        inti();
        DateT.setOnClickListener(new View.OnClickListener() {
           // @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(
                        LostObjectDetailsActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        DateSet,
                        year,month,Day
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }

        });
        DateSet = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month++;
                String todayDate = year +"/"+month+"/"+day;
                DateT.setText(todayDate);
            }
        };
    }

    protected void inti()
    {

        DateT =  findViewById(R.id.Date);
        Calendar cal = Calendar.getInstance();//To get today's date
        year= cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        Day = cal.get(Calendar.DAY_OF_MONTH);
        String todayDate = year +"/"+(month+1)+"/"+Day;
        DateT.setText(todayDate);
    }
}