package com.helloworld.goodpoint.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.helloworld.goodpoint.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LostObjectDetailsActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView DateT ;
    private Button Person , Object;
    private Fragment PersonF,ObjectF;
    FragmentManager FM ;
    FragmentTransaction FT;
    private DatePickerDialog.OnDateSetListener DateSet;
    private AutoCompleteTextView autoCom;
    private int year, month, Day;

    List<String> list;

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
                if (y > year || m > month || d > Day) {
                    Toast.makeText(LostObjectDetailsActivity.this, "Unknown date", Toast.LENGTH_LONG).show();
                    String todayDate = year + "/" + (month + 1) + "/" + Day;
                    DateT.setText(todayDate);
                } else {

                    String Date = y + "/" + m + "/" + d;
                    DateT.setText(Date);
                }
            }
        };

    }

    protected void prepareList() {
        list = new ArrayList<>();
        list.add(getString(R.string.Cairo));
        list.add(getString(R.string.Alexandria));
        list.add(getString(R.string.ShubraElKheima));
        list.add(getString(R.string.Giza));
        list.add(getString(R.string.PortSaid));
        list.add(getString(R.string.Suez));
        list.add(getString(R.string.ElMahallaElKubra));
        list.add(getString(R.string.Luxor));
        list.add(getString(R.string.Mansoura));
        list.add(getString(R.string.Tanta));
        list.add(getString(R.string.Asyut));
        list.add(getString(R.string.Ismailia));
        list.add(getString(R.string.Faiyum));
        list.add(getString(R.string.Zagazig));
        list.add(getString(R.string.Damietta));
        list.add(getString(R.string.Aswan));
        list.add(getString(R.string.Minya));
        list.add(getString(R.string.BeniSuef));
        list.add(getString(R.string.Hurghada));
        list.add(getString(R.string.Qena));
        list.add(getString(R.string.Sohag));
        list.add(getString(R.string.ShibinElKom));
        list.add(getString(R.string.Banha));
        list.add(getString(R.string.Arish));

    }
    @Override
    public void onClick(View view) {
         FM = getFragmentManager();
         FT= FM.beginTransaction();
        if(view == Person)
        {
            FT.replace(R.id.FragmentID,PersonF,null);
        }
        else if(view == Object)
        {
            FT.replace(R.id.FragmentID,ObjectF,null);
        }
        FT.commit();

    }
    protected void inti() {

        DateT = findViewById(R.id.Date);
        autoCom = findViewById(R.id.auto);
        Person = findViewById(R.id.Person);
        Object = findViewById(R.id.Object);
        Person.setOnClickListener(this);
        Object.setOnClickListener(this);
        PersonF = new PersonFragment();
        ObjectF = new ObjectFragment();
        Calendar cal = Calendar.getInstance();//To get today's date
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        Day = cal.get(Calendar.DAY_OF_MONTH);
        String todayDate = year + "/" + (month + 1) + "/" + Day;
        DateT.setText(todayDate);
        prepareList();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        autoCom.setThreshold(1);//start working from first char
        autoCom.setAdapter(adapter);

    }


}