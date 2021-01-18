package com.helloworld.goodpoint.ui.lostFoundObject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.helloworld.goodpoint.R;
import com.helloworld.goodpoint.ui.prepareList;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class FoundObjectActivity extends AppCompatActivity implements View.OnClickListener,objectDataType {
    private TextView DateFound;
    private EditText Location;
    private DatePickerDialog.OnDateSetListener DateSet;
    private int year, month, Day;
    private Button Person;
    private Button Object;
    private Button MatchFound;
    private prepareList List ;
    private List<String> listColor ;
    private Fragment PersonF, ObjectF;
    private String location;
    private String ObjectColor,Serial,brand,textArea_information,Type;
    private String PName;
    private List<Bitmap> Person_Images;
    double Latitude;
    double Longitude;
    int place_picker_request = 1;
    FusedLocationProviderClient fusedLocationProviderClient;
    private boolean flagPerson,flagObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_object);
        inti();
          DateSet = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                m++;
                if (y > year || (m-1 > month && y >= year)|| (d > Day && m-1 >= month && y >= year)) {
                    FancyToast.makeText(FoundObjectActivity.this,"Invalid date",FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
                    String todayDate = year + "/" + (month + 1) + "/" + Day;
                    DateFound.setText(todayDate);
                } else {

                    String Date = y + "/" + m + "/" + d;
                    DateFound.setText(Date);
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(FoundObjectActivity.this);
        if (ActivityCompat.checkSelfPermission(FoundObjectActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(FoundObjectActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(FoundObjectActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},12);
        }
        else
        {
            getCurrentLocation();
        }
    }

    @Override
    public void onClick(View view) {
        FragmentManager FM = getFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();
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
            case R.id.FoundLocatin:
                PopupMenu popupMenu = new PopupMenu(this, view);
                popupMenu.getMenuInflater().inflate(R.menu.choose_location, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (ActivityCompat.checkSelfPermission(FoundObjectActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                && ActivityCompat.checkSelfPermission(FoundObjectActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(FoundObjectActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 12);
                        }
                        else {
                           /* if (!isNetworkAvailable(FoundObjectActivity.this) || !isInternetAvailable()) {
                                Toast.makeText(FoundObjectActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                            }*/
                            switch (item.getItemId()) {
                                    case R.id.TakeCurrLocation:
                                        Geocoder geocoder = new Geocoder(FoundObjectActivity.this, new Locale("en"));
                                        try {
                                            List<Address> addresses = geocoder.getFromLocation(Latitude, Longitude, 1);
                                            String Country = addresses.get(0).getCountryName();
                                            String City = addresses.get(0).getAdminArea();
                                            String area = addresses.get(0).getLocality();
                                            String Locate = area + "," + City + "," + Country + ".";
                                            Location.setText(Locate);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                    case R.id.DeteLocation:
                                        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                                        try {
                                            Intent intent = builder.build(FoundObjectActivity.this);
                                            startActivityForResult(intent, place_picker_request);
                                        } catch (GooglePlayServicesRepairableException e) {
                                            e.printStackTrace();
                                            Log.e("Crash", "onMenuItemClick: " + e.getMessage());
                                        } catch (GooglePlayServicesNotAvailableException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                }
                            }
                        return true;
                     }
                });
                popupMenu.show();
                break;
            case R.id.PersonFound:
                flagPerson = true;
                flagObject = false;
                FT.replace(R.id.FragmentFoundID,PersonF,null);
                Person.setTextColor(0xFFF38E3A);
                Object.setTextColor(Color.BLACK);

                FT.commit();
                break;
            case R.id.ObjectFound:
                flagObject = true;
                flagPerson = false;
                FT.replace(R.id.FragmentFoundID,ObjectF,null);
                Object.setTextColor(0xFFF38E3A);
                Person.setTextColor(Color.BLACK);

                FT.commit();
                break;
            case R.id.MatchFound:
                if (!flagObject && !flagPerson) {
                    FancyToast.makeText(this,"Specify the type of the missing object",FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
                }
                else if(flagObject&&CheckMatchObject()) {
                    FancyToast.makeText(this,"The data has been saved successfully",FancyToast.LENGTH_LONG, FancyToast.SUCCESS,false).show();
                }
                else if(flagPerson&&CheckMatchPerson())
                {
                    FancyToast.makeText(this,"The data has been saved successfully",FancyToast.LENGTH_LONG, FancyToast.SUCCESS,false).show();
                }
                break;
        }
    }
    private boolean CheckMatchPerson()
    {
        EditText PersonName =  PersonF.getView().findViewById(R.id.PersonName);
        PName = PersonName.getText().toString();
        location = Location.getText().toString();
        if(location.isEmpty())
        {
            FancyToast.makeText(this,"Specify where you found this object",FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
            return false;
        }
        else if(Person_Images.size() == 0)
        {
            FancyToast.makeText(this,"You must put at least one picture!",FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
            return false;
        }
        return true;
    }
    private boolean CheckMatchObject()
    {
        location = Location.getText().toString();
        AutoCompleteTextView V =  ObjectF.getView().findViewById(R.id.ColorOfObject);
        EditText serialObject =  ObjectF.getView().findViewById(R.id.Serial);
        EditText brandObject =  ObjectF.getView().findViewById(R.id.brand);
        EditText textArea_informationObject =  ObjectF.getView().findViewById(R.id.textArea_information);
        EditText TypeObject;

        ObjectColor = V.getText().toString();
        Serial = serialObject.getText().toString();
        brand = brandObject.getText().toString();
        textArea_information = textArea_informationObject.getText().toString();
        if(location.isEmpty())
        {
            FancyToast.makeText(this,"Specify where you found this object",FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
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
        else if(textArea_information.isEmpty())
        {
            textArea_informationObject.setError("Field can't be empty");
            return false;
        }
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == place_picker_request && resultCode == RESULT_OK)
        {
            Place place = PlacePicker.getPlace(data,this);
            StringBuilder stringBuilder = new  StringBuilder();
            Latitude = place.getLatLng().latitude;
            Longitude = place.getLatLng().longitude;
            Geocoder geocoder = new Geocoder(FoundObjectActivity.this, new Locale("en"));
            try {
                List<Address> addresses = geocoder.getFromLocation(Latitude,Longitude,1);
                String Country = addresses.get(0).getCountryName();
                String City = addresses.get(0).getAdminArea();
                String area = addresses.get(0).getLocality();
                String Locate = area + ","+ City + "," + Country + ".";
                Location.setText(Locate);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 12 && (grantResults.length > 0) &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            getCurrentLocation();
        }
        else if(requestCode == 12 && (grantResults.length > 0) &&
                grantResults[0] == PackageManager.PERMISSION_DENIED)
        {

            FancyToast.makeText(this,"Permission denied",FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
        }
        else if(requestCode == 11 && (grantResults.length > 0) &&
                grantResults[0] == PackageManager.PERMISSION_DENIED)
        {
            FancyToast.makeText(this,"Permission denied",FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
        }

    }
    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        )
        {
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @SuppressLint("MissingPermission")
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if(location != null) {
                        Longitude = location.getLongitude();
                        Latitude = location.getLatitude();
                    }
                    else
                    {
                        @SuppressLint("RestrictedApi") LocationRequest locationRequest = new LocationRequest()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(10000)
                                .setFastestInterval(1000)
                                .setNumUpdates(1);
                        LocationCallback locationCallback = new LocationCallback(){
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                Location location1 = locationResult.getLastLocation();
                                Longitude = location1.getLongitude();
                                Latitude = location1.getLatitude();
                            }
                        };
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper());

                    }
                }
            });
        }

        else
        {
            //when location servies is not enabled
            //open location setting
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        boolean connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
        return connected;
    }
    public boolean isInternetAvailable() {
        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }
    }
    protected void inti() {

        DateFound = findViewById(R.id.DateFound);
        Button foundLocatin = findViewById(R.id.FoundLocatin);
        Person = findViewById(R.id.PersonFound);
        Object = findViewById(R.id.ObjectFound);
        MatchFound = findViewById(R.id.MatchFound);
        Location = findViewById(R.id.Location);
        DateFound.setOnClickListener(this);
        foundLocatin.setOnClickListener(this);
        Person.setOnClickListener(this);
        Object.setOnClickListener(this);
        MatchFound.setOnClickListener(this);
        PersonF = new PersonFragment();
        ObjectF = new ObjectFragment();
        List = new prepareList();
        listColor = List.prepareListColor(this);
        Calendar cal = Calendar.getInstance();//To get today's date
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        Day = cal.get(Calendar.DAY_OF_MONTH);
        String todayDate = year + "/" + (month + 1) + "/" + Day;
        DateFound.setText(todayDate);
    }

    @Override
    public void getType(String T) { Type = T; }
    @Override
    public void getImageCheck(Boolean check) { }
    @Override
    public void getBitmap_Image(Bitmap Bitmap_Image) { }
    @Override
    public void getBitmap_ImagePersonImages(java.util.List<Bitmap> PImages) { Person_Images = PImages; }
}