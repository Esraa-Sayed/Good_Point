package com.helloworld.goodpoint.ui.lostFoundObject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.helloworld.goodpoint.R;
import com.helloworld.goodpoint.ui.prepareList;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

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
    private String PName;
    private Bitmap Bitmap_Image;
    private List<Bitmap> Person_Images;
    private List<Uri> Person_Images_uri;
    private boolean flagPerson,flagObject,CheckImageObeject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_object_details);
        inti();
        Calendar cal = Calendar.getInstance();//To get today's date
        inti();
        if (savedInstanceState != null) {
            year = savedInstanceState.getInt("year");
            month = savedInstanceState.getInt("month");
            Day = savedInstanceState.getInt("Day");
            flagPerson = savedInstanceState.getBoolean("flagPerson");
            flagObject = savedInstanceState.getBoolean("flagObject");
            if(flagPerson == true)
            {
                Person.setTextColor(0xFFF38E3A);
                Object.setTextColor(Color.BLACK);
            }
            else if(flagObject== true)
            {
                Object.setTextColor(0xFFF38E3A);
                Person.setTextColor(Color.BLACK);
            }
        }
        else {
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            Day = cal.get(Calendar.DAY_OF_MONTH);
        }
        String todayDate = year + "/" + (month + 1) + "/" + Day;
        DateT.setText(todayDate);
        DateSet = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                m++;
                if (y > year || (m-1 > month && y >= year)|| (d > Day && m-1 >= month && y >= year)) {
                    FancyToast.makeText(LostObjectDetailsActivity.this,"Invalid date",FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
                    String todayDate = year + "/" + (month + 1) + "/" + Day;
                    DateT.setText(todayDate);
                } else {
                    year = y;
                    month = m-1;
                    Day = d;
                    String Date = y + "/" + m + "/" + d;
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
                   FancyToast.makeText(this,"Specify the type of the missing object",FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
               }
               else if(flagObject&&CheckMatchObject()) {
                       FancyToast.makeText(this,"The data has been saved successfully",FancyToast.LENGTH_LONG, FancyToast.SUCCESS,false).show();
                       finish();
               }
               else if(flagPerson&&CheckMatchPerson())
               {
                   FancyToast.makeText(this,"The data has been saved successfully",FancyToast.LENGTH_LONG, FancyToast.SUCCESS,false).show();
                   //finish();
               }
                break;
        }
    }
    private boolean CheckMatchPerson()
    {
        EditText PersonName =  PersonF.getView().findViewById(R.id.PersonName);
        PName = PersonName.getText().toString();
        City = autoCom.getText().toString();
        if(City.isEmpty()) {
            autoCom.setError("Field can't be empty");
            return false;
        }
        else if (!list.contains(City.trim())) {
            autoCom.setError("Please Enter a valid city!");
            return false;
        }
        if(PName.isEmpty())
        {
            PersonName.setError("Field can't be empty");
            return false;
        }
        else if(Person_Images.size() == 0)
        {
            FancyToast.makeText(this,"You must put at least one picture!",FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
            return false;
        }
        else if(Person_Images_uri.size()>0)
        {
           for (int i=0;i<Person_Images_uri.size();i++)
           {
               String path = getRealPathFromURI_API19(this,Person_Images_uri.get(i));
               Log.e("Path", "CheckMatchPerson: "+path);
           }
        }
        return true;
    }
    public String getRealPathFromURI_API19(Context context, Uri uri) {
        String filePath = "";
        if (uri.getHost().contains("com.android.providers.media")) {
            // Image pick from recent
            String wholeID = DocumentsContract.getDocumentId(uri);

            // Split at colon, use second item in the array
            String id = wholeID.split(":")[1];

            String[] column = {MediaStore.Images.Media.DATA};

            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";

            Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    column, sel, new String[]{id}, null);

            int columnIndex = cursor.getColumnIndex(column[0]);

            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
            return filePath;
        } else {
            // image pick from gallery
            return  getPath(uri);
        }
    }
    public   String getPath(Uri uri)
    {
        if(uri != null)
        {
           String[] projection = {MediaStore.Images.Media.DATA};
           Cursor cursor = getContentResolver().query(uri,projection,null,null,null);
           if(cursor != null)
           {
              int col_in = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
              cursor.moveToFirst();
              return  cursor.getString(col_in);
           }
        }
        else return null;
        return uri.getPath();
    }
    private boolean CheckMatchObject()
    {
        AutoCompleteTextView V =  ObjectF.getView().findViewById(R.id.ColorOfObject);
        EditText serialObject =  ObjectF.getView().findViewById(R.id.Serial);
        EditText brandObject =  ObjectF.getView().findViewById(R.id.brand);
        EditText textArea_informationObject =  ObjectF.getView().findViewById(R.id.textArea_information);
        EditText TypeObject;
        City = autoCom.getText().toString();
        ObjectColor = V.getText().toString();
        Serial = serialObject.getText().toString();
        brand = brandObject.getText().toString();
        textArea_information = textArea_informationObject.getText().toString();
        if(City.isEmpty()) {
            autoCom.setError("Field can't be empty");
            return false;
        }
        else if (!list.contains(City.trim())) {
            autoCom.setError("Please Enter a valid city!");
            return false;
        }
        else if (Type.equals("Type")) {
            FancyToast.makeText(this,"You must Choose the Type!",FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
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

        else if (!listColor.contains(ObjectColor.trim())) {
            V.setError("Color isn't known!");
            return false;
        }
        else if(CheckImageObeject && Bitmap_Image == null)
        {
            FancyToast.makeText(this,"You should put the image to the item!",FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
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
    @Override
    public void getBitmap_ImagePersonImages(List<Bitmap> PImages,List<Uri> Uri_images){ Person_Images = PImages;
        Person_Images_uri = Uri_images;
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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        autoCom.setThreshold(1);//start working from first char
        autoCom.setAdapter(adapter);
        flagPerson = false;
        flagObject = false;
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("year", year);
        outState.putInt("month",  month);
        outState.putInt("Day", Day);
        outState.putBoolean("flagPerson",flagPerson);
        outState.putBoolean("flagObject",flagObject);
    }
}