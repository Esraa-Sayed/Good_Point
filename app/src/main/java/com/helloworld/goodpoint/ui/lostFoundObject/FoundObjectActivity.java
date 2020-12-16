package com.helloworld.goodpoint.ui.lostFoundObject;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.helloworld.goodpoint.R;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class FoundObjectActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView DateFound;
    private EditText Location;
    private DatePickerDialog.OnDateSetListener DateSet;
    private int year, month, Day;
    private Button Person;
    private Button Object;
    private Fragment PersonF, ObjectF;
     double Latitude;
     double Longitude;
    FusedLocationProviderClient fusedLocationProviderClient;
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
                        switch (item.getItemId()) {
                            case R.id.TakeCurrLocation:

                                Geocoder geocoder = new Geocoder(FoundObjectActivity.this, Locale.getDefault());
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
                                break;
                            case R.id.DeteLocation:
                               Intent intent = new Intent(FoundObjectActivity.this, detect_location.class);
                               intent.putExtra("Latitude",Latitude);
                               intent.putExtra("Longitude",Longitude);
                               startActivity(intent);
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
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
            Toast.makeText(this,"Permission denied",Toast.LENGTH_SHORT).show();
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
                        LocationRequest locationRequest = new LocationRequest()
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
    protected void inti() {

        DateFound = findViewById(R.id.DateFound);
        Button foundLocatin = findViewById(R.id.FoundLocatin);
        Person = findViewById(R.id.PersonFound);
        Object = findViewById(R.id.ObjectFound);
        Location = findViewById(R.id.Location);
        DateFound.setOnClickListener(this);
        foundLocatin.setOnClickListener(this);
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