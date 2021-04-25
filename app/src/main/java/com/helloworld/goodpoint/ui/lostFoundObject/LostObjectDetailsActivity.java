package com.helloworld.goodpoint.ui.lostFoundObject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.helloworld.goodpoint.R;
import com.helloworld.goodpoint.ui.prepareList;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

public class LostObjectDetailsActivity extends AppCompatActivity implements View.OnClickListener,objectDataType{
    private TextView DateT ;
    private File caseFile ;
    private CascadeClassifier faceDetector;
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
            if(flagPerson)
            {
                Person.setTextColor(0xFFF38E3A);
                Object.setTextColor(Color.BLACK);
            }
            else if(flagObject)
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
    Mat sampledImg;
    private boolean CheckMatchPerson() {
        EditText PersonName = PersonF.getView().findViewById(R.id.PersonName);
        ImageView IV = PersonF.getView().findViewById(R.id.imageView);
        PName = PersonName.getText().toString();
        City = autoCom.getText().toString();
        if (City.isEmpty()) {
            autoCom.setError("Field can't be empty");
            return false;
        } else if (!list.contains(City.trim())) {
            autoCom.setError("Please Enter a valid city!");
            return false;
        }
        if (PName.isEmpty()) {
            PersonName.setError("Field can't be empty");
            return false;
        } else if (Person_Images.size() == 0) {
            FancyToast.makeText(this, "You must put at least one picture!", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            return false;
        } else {
            for (int i = 0; i < Person_Images.size(); i++) {
                Bitmap My = Person_Images.get(i);
                FaceDetector faceDetector = new FaceDetector.Builder(this)
                        .setTrackingEnabled(false)
                        .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                        .setMode(FaceDetector.FAST_MODE).build();
                Bitmap faceBitmap = Bitmap.createBitmap(My.getWidth(), My.getHeight(), Bitmap.Config.RGB_565);
                if (!faceDetector.isOperational()) {
                    Toast.makeText(this, "Face Detection can't be setup", Toast.LENGTH_SHORT).show();
                } else {
                    Frame frame = new Frame.Builder().setBitmap(My).build();
                    SparseArray<Face> sparseArray = faceDetector.detect(frame);
                    for (int j = 0; j < sparseArray.size(); j++) {
                        Face face = sparseArray.valueAt(j);
                        if (((int) face.getPosition().y + (int) face.getHeight()) > My.getHeight())
                        {
                            int H = My.getHeight() - (int) face.getPosition().y;
                            faceBitmap = Bitmap.createBitmap(My, (int) face.getPosition().x, (int) face.getPosition().y, (int) face.getWidth(), H);
                        } else if (((int) face.getPosition().x + (int) face.getWidth()) > My.getWidth())
                        {
                            int W = My.getWidth() - (int) face.getPosition().x;
                            faceBitmap = Bitmap.createBitmap(My, (int) face.getPosition().x, (int) face.getPosition().y, W, (int) face.getHeight());
                        }
                        else if ((((int) face.getPosition().x + (int) face.getWidth()) > My.getWidth()) && (((int) face.getPosition().y + (int) face.getHeight()) > My.getHeight())) {
                            int H = My.getHeight() - (int) face.getPosition().y;
                            int W = My.getWidth() - (int) face.getPosition().x;
                            faceBitmap = Bitmap.createBitmap(My, (int) face.getPosition().x, (int) face.getPosition().y, W, H);
                        } else {
                            faceBitmap = Bitmap.createBitmap(My, (int) face.getPosition().x, (int) face.getPosition().y, (int) face.getWidth(), (int) face.getHeight());

                        }
                    }
                    IV.setVisibility(View.VISIBLE);
                    IV.setImageBitmap(faceBitmap);
                }
            }
        }
        return true;
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
    public void getBitmap_ImagePersonImages(List<Bitmap> PImages){ Person_Images = PImages;
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