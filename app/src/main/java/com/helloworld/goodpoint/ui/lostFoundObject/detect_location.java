package com.helloworld.goodpoint.ui.lostFoundObject;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.helloworld.goodpoint.R;

public class detect_location extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double Latitude;
    double Longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        Latitude = intent.getExtras().getDouble("Latitude");
        Longitude = intent.getExtras().getDouble("Longitude");
        Toast.makeText(this,Latitude + "  "+Longitude,Toast.LENGTH_LONG);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng curLocation = new LatLng(Latitude, Longitude);
        mMap.addMarker(new MarkerOptions().position(curLocation).title("Your current location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLocation,15));
    }
}