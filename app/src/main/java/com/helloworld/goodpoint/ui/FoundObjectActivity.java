package com.helloworld.goodpoint.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.helloworld.goodpoint.R;
import com.helloworld.goodpoint.ui.lostObject.LostObjectDetailsActivity;
import com.helloworld.goodpoint.ui.lostObject.ObjectFragment;
import com.helloworld.goodpoint.ui.lostObject.PersonFragment;

import java.util.Calendar;

public class FoundObjectActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView DateFound ;
    private DatePickerDialog.OnDateSetListener DateSet;
    private int year, month, Day;
    private Button Person , Object;
    private Fragment PersonF,ObjectF;
    FragmentManager FM ;
    FragmentTransaction FT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_object);
        inti();
        DateSet = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                m++;
                if (y > year || (m > month && y >= year)|| (d > Day && m >= month && y >= year)) {
                    Toast.makeText(FoundObjectActivity.this, "Invalid date", Toast.LENGTH_LONG).show();
                    String todayDate = year + "/" + (month + 1) + "/" + Day;
                    DateFound.setText(todayDate);
                } else {

                    String Date = d + "/" + m + "/" + y;
                    DateFound.setText(Date);
                }
            }
        };
    }
    @Override
    public void onClick(View view) {
        FM = getFragmentManager();
        FT= FM.beginTransaction();
        switch (view.getId() ) {
            case R.id.DateFound:
                DatePickerDialog dialog = new DatePickerDialog(
                        FoundObjectActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        DateSet,
                        year, month, Day
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                break;
            case R.id.PersonFound:
                FT.replace(R.id.FragmentFoundID,PersonF,null);
                Person.setTextColor(0xFFF38E3A);
                Object.setTextColor(Color.BLACK);

                FT.commit();
                break;
            case R.id.ObjectFound:
                FT.replace(R.id.FragmentFoundID,ObjectF,null);
                Object.setTextColor(0xFFF38E3A);
                Person.setTextColor(Color.BLACK);

                FT.commit();
                break;
        }
    }
    protected void inti() {

        DateFound = findViewById(R.id.DateFound);
        DateFound.setOnClickListener(this);
        Person = findViewById(R.id.PersonFound);
        Object = findViewById(R.id.ObjectFound);
        Person.setOnClickListener(this);
        Object.setOnClickListener(this);
        PersonF = new PersonFragment();
        ObjectF = new ObjectFragment();
        Calendar cal = Calendar.getInstance();//To get today's date
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        Day = cal.get(Calendar.DAY_OF_MONTH);
        String todayDate = Day + "/" + (month + 1) + "/" + year;
        DateFound.setText(todayDate);
    }

}