package com.helloworld.goodpoint.ui.lostFoundObject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.helloworld.goodpoint.R;
import com.helloworld.goodpoint.ui.prepareList;

import java.util.Calendar;
import java.util.List;

public class LostObjectDetailsActivity extends AppCompatActivity implements View.OnClickListener,objectDataType{
    private TextView DateT ;
    private Button Person , Object,Match;
    private Fragment PersonF,ObjectF;
    private FragmentManager FM ;
    private FragmentTransaction FT;
    private DatePickerDialog.OnDateSetListener DateSet;
    private AutoCompleteTextView autoCom;
    private int year, month, Day;
    private prepareList List ;
    private List<String> list,listColor ;
    private String City,ObjectColor,Serial,brand,textArea_information,Type;
    Bitmap Bitmap_Image;
    EditText TypeObject;
    private Spinner spinner;
    private boolean flagPerson,flagObject,CheckImageObeject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_object_details);
        inti();
        DateSet = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                m++;
                if (y > year || (m > month && y >= year)|| (d > Day && m >= month && y >= year)) {
                    Toast.makeText(LostObjectDetailsActivity.this, "Invalid date", Toast.LENGTH_LONG).show();
                    String todayDate = year + "/" + (month + 1) + "/" + Day;
                    DateT.setText(todayDate);
                } else {

                    String Date = d + "/" + m + "/" + y;
                    DateT.setText(Date);
                }
            }
        };

    }
    @Override
    public void onClick(View view) {
         FM = getFragmentManager();
         FT= FM.beginTransaction();
       switch (view.getId() ) {
           case R.id.Person:
               FT.replace(R.id.FragmentID, PersonF, null);
               Person.setTextColor(0xFFF38E3A);
               Object.setTextColor(Color.BLACK);

               FT.commit();
               flagPerson = true;
               flagObject = false;
               break;
           case R.id.Object:
               FT.replace(R.id.FragmentID, ObjectF, "object");
               Object.setTextColor(0xFFF38E3A);
               Person.setTextColor(Color.BLACK);

               FT.commit();
               flagObject = true;
               flagPerson = false;
               break;
           case R.id.Date:
               DatePickerDialog dialog = new DatePickerDialog(
                       LostObjectDetailsActivity.this,
                       android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                       DateSet,
                       year, month, Day
               );
               dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
               dialog.show();
               break;
           case R.id.Match:
               if (!flagObject && !flagPerson) {
                   Toast.makeText(this, "Specify the type of the missing object", Toast.LENGTH_SHORT).show();
               }
               else if(flagObject) {
                   if(CheckMatchObject())
                   {
                       Toast.makeText(this, "The data has been saved successfully", Toast.LENGTH_LONG).show();
                   }
               }
               else if(flagPerson)
               {
                   if(CheckMatchPerson())
                   {
                       Toast.makeText(this, "The data has been saved successfully", Toast.LENGTH_LONG).show();
                   }
               }
                break;
        }
    }
    private boolean CheckMatchPerson()
    {
        return true;
    }
    private boolean CheckMatchObject()
    {
        AutoCompleteTextView V =  ObjectF.getView().findViewById(R.id.ColorOfObject);
        EditText serialObject =  ObjectF.getView().findViewById(R.id.Serial);
        EditText brandObject =  ObjectF.getView().findViewById(R.id.brand);
        EditText textArea_informationObject =  ObjectF.getView().findViewById(R.id.textArea_information);

        City = autoCom.getText().toString();
        ObjectColor = V.getText().toString();
        Serial = serialObject.getText().toString();
        brand = brandObject.getText().toString();
        textArea_information = textArea_informationObject.getText().toString();
        if(City.isEmpty()) {
            autoCom.setError("Field can't be empty");
            return false;
        }
        else if (!list.contains(City)) {
            autoCom.setError("Please Enter a valid city!");
            return false;
        }
        else if (Type.equals("Type")) {
            Toast.makeText(this,"You must Choose the Type!",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(Type.equals("Others"))
        {
            TypeObject =  ObjectF.getView().findViewById(R.id.Other);
            Type = TypeObject.getText().toString();
            if(Type.isEmpty())
            {
                TypeObject.setError("Field can't be empty");
                return false;
            }
        }
        else if(brand.isEmpty())
        {
            brandObject.setError("Field can't be empty");
            return false;
        }
        else if(ObjectColor.isEmpty())
        {
            V.setError("Field can't be empty");
            return false;
        }

        else if (!listColor.contains(ObjectColor)) {
            V.setError("Color isn't known!");
            return false;
        }
        else if(CheckImageObeject && Bitmap_Image == null)
        {
                Toast.makeText(this,"You should put the image to the item!",Toast.LENGTH_SHORT).show();
                return false;
        }
        else if(textArea_information.isEmpty())
        {
            textArea_informationObject.setError("Field can't be empty");
            return false;
        }
        return true;

    }
    @Override
    public void getType(String T) {
        Type = T;
    }
    @Override
    public void getImageCheck(Boolean Check)
    {
        CheckImageObeject = Check;
    }
    @Override
    public void getBitmap_Image(Bitmap BImage)
    {
        Bitmap_Image = BImage;
    }
    protected void inti() {
        DateT = findViewById(R.id.Date);
        autoCom = findViewById(R.id.auto);
        Person = findViewById(R.id.Person);
        Object = findViewById(R.id.Object);
        Match = findViewById(R.id.Match);
        Person.setOnClickListener(this);
        Object.setOnClickListener(this);
        Match.setOnClickListener(this);
        DateT.setOnClickListener(this);
        PersonF = new PersonFragment();
        ObjectF = new ObjectFragment();
        List = new prepareList();
        list = List.prepareList(this);
        listColor = List.prepareListColor(this);
        Calendar cal = Calendar.getInstance();//To get today's date
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        Day = cal.get(Calendar.DAY_OF_MONTH);
        String todayDate = Day + "/" + (month + 1) + "/" + year;
        DateT.setText(todayDate);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        autoCom.setThreshold(1);//start working from first char
        autoCom.setAdapter(adapter);
        flagPerson = false;
        flagObject = false;
    }
}