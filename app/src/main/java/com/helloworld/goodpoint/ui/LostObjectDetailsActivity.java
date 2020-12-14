package com.helloworld.goodpoint.ui;

import androidx.appcompat.app.AppCompatActivity;

<<<<<<< HEAD
=======
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
<<<<<<< HEAD
import android.os.Build;
>>>>>>> parent of 139933f... Complete choose city
=======
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
>>>>>>> parent of a1f5c59... Person-Object Fragments
import android.os.Bundle;

import com.helloworld.goodpoint.R;

<<<<<<< HEAD
public class LostObjectDetailsActivity extends AppCompatActivity {

=======
import java.util.Calendar;

public class LostObjectDetailsActivity extends AppCompatActivity {
<<<<<<< HEAD
    private TextView DateT ;
    private DatePickerDialog.OnDateSetListener DateSet;
    int year , month , Day;
>>>>>>> parent of 139933f... Complete choose city
=======
    private TextView DateT;
    private DatePickerDialog.OnDateSetListener DateSet;
    private AutoCompleteTextView autoCom;
    private int year, month, Day;
    List<String> list;

>>>>>>> parent of a1f5c59... Person-Object Fragments
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_object_details);
<<<<<<< HEAD
=======
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

<<<<<<< HEAD
    protected void inti()
    {

        DateT =  findViewById(R.id.Date);
=======
    protected void inti() {

        DateT = findViewById(R.id.Date);
        autoCom = findViewById(R.id.auto);
>>>>>>> parent of a1f5c59... Person-Object Fragments
        Calendar cal = Calendar.getInstance();//To get today's date
        year= cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        Day = cal.get(Calendar.DAY_OF_MONTH);
        String todayDate = year +"/"+(month+1)+"/"+Day;
        DateT.setText(todayDate);
>>>>>>> parent of 139933f... Complete choose city
    }
}