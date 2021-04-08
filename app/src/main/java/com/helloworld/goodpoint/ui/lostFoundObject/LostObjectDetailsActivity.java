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
    Mat sampledImg;
    private boolean CheckMatchPerson()
    {
        if(!OpenCVLoader.initDebug())
        {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0,this,baseCallback);
        }
        else
        {
            try {
                baseCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        EditText PersonName =  PersonF.getView().findViewById(R.id.PersonName);
        ImageView IV = PersonF.getView().findViewById(R.id.imageView);
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
               PathImg I = new PathImg();
               String path = I.getRealPathFromURI_API19(this,Person_Images_uri.get(i));
               Mat originalImg = Imgcodecs.imread(path);//BGR format
               Mat rgbImg = new Mat();
               //convert BGR to RGB
               Imgproc.cvtColor(originalImg,rgbImg,Imgproc.COLOR_BGR2RGB);
               Display display = getWindowManager().getDefaultDisplay();
               Point size = new Point();
               display.getSize(size);
               int mobile_width = size.x;
               int mobile_height = size.y;
               sampledImg = new Mat();
              double downSampleRatio = calculateSubSimpleSize(rgbImg,mobile_width,mobile_height);
//              Imgproc.resize(rgbImg,sampledImg,new Size(),downSampleRatio,downSampleRatio,Imgproc.INTER_AREA);
               try {
                   ExifInterface exifInterface = new ExifInterface(path);
                   int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,1);
                   switch (orientation)
                   {
                       case ExifInterface.ORIENTATION_ROTATE_90:
                           sampledImg = sampledImg.t();
                           Core.flip(sampledImg,sampledImg,1);
                           break;
                           case ExifInterface.ORIENTATION_ROTATE_270:
                               sampledImg = sampledImg.t();
                               Core.flip(sampledImg,sampledImg,0);
                               break;
                   }
               } catch (IOException e) {
                   e.printStackTrace();
               }
               MatOfRect faceDetections = new MatOfRect();
               faceDetector.detectMultiScale(rgbImg,faceDetections);
               for(Rect rect:faceDetections.toArray())
               {

                      Imgproc.rectangle(rgbImg,new org.opencv.core.Point(rect.x,rect.y),
                              new org.opencv.core.Point(rect.x+rect.width,rect.y+rect.height),
                              new Scalar(0,255,0));
               }
               Bitmap bitmap = Bitmap.createBitmap(rgbImg.cols(),rgbImg.rows(),Bitmap.Config.RGB_565);
               Utils.matToBitmap(rgbImg,bitmap);
               IV.setVisibility(View.VISIBLE);
               IV.setImageBitmap(bitmap);
               Log.e("Path", "CheckMatchPerson: "+path);
           }
        }
        return true;
    }
private BaseLoaderCallback baseCallback = new BaseLoaderCallback(this) {
    @Override
    public void onManagerConnected(int status) throws IOException {
        switch (status) {
            case LoaderCallbackInterface.SUCCESS: {
                InputStream is = getResources().openRawResource(R.raw.haarcascade_frontalface_alt2);
                File CasCadeDire = getDir("cascade", Context.MODE_PRIVATE);
                 caseFile = new File(CasCadeDire,"haarcascade_frontalface_alt2.xml");
                FileOutputStream fos = new FileOutputStream(caseFile);
                byte[] buffer = new byte[4096];
                int byteRead;
                while((byteRead = is.read(buffer))!=-1)
                {
                    fos.write(buffer,0,byteRead);
                }
                is.close();
                fos.close();
                faceDetector = new CascadeClassifier(caseFile.getAbsolutePath());
                if(faceDetector.empty())
                {
                    faceDetector = null;
                }
                else
                {
                    CasCadeDire.delete();
                }
            }
            break;
            default:
                super.onManagerConnected(status);
                break;
        }

    }
};
    private double calculateSubSimpleSize(Mat rgbImg, int mobile_width, int mobile_height) {
        final int width = rgbImg.width();
        final int height = rgbImg.height();
        double inSampleSize = 1;
        if(width > mobile_width || height > mobile_height)
        {
            final double heightRatio = (double) mobile_height/(double)height;
            final double widthRatio = (double) mobile_width/(double)width;
            inSampleSize = heightRatio < width ? height : width;
        }
        return inSampleSize;
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