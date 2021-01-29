package com.helloworld.goodpoint.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.helloworld.goodpoint.R;
import com.helloworld.goodpoint.pojo.ObjectLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MapFragment extends Fragment {

    private static final int MAP_CODE = 1;
    SupportMapFragment mapFragment;
    FusedLocationProviderClient client;
    Location curLocation;
    String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    List<ObjectLocation> list;
    Map<Marker,Integer>marker_id;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @SuppressLint("MissingPermission")
        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng curLatLng = new LatLng(curLocation.getLatitude(), curLocation.getLongitude());

            googleMap.setMyLocationEnabled(true);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLatLng, 15));

            list = getLocations();

            for(ObjectLocation object: list) {
                Marker marker = googleMap.addMarker(new MarkerOptions().position(object.getLatLng()));
                marker_id.put(marker,object.getUserId());
            }

            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Intent intent = new Intent();//getContext(),ProfileActivity.class
                    intent.putExtra("ID",marker_id.get(marker));
                    Toast.makeText(getContext(), ""+marker_id.get(marker), Toast.LENGTH_SHORT).show();
                    //startActivity(intent);
                    return false;
                }
            });

        }
    };

    private List<ObjectLocation> getLocations() {
        List<ObjectLocation>ret = new ArrayList<>();
        Random random = new Random();
        for(int i=0;i<100;i++)
            ret.add(new ObjectLocation(25+random.nextDouble()*10,22+random.nextDouble()*10,i));
        return ret;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        runGoogleMap();

    }

    private void runGoogleMap() {
        if (ActivityCompat.checkSelfPermission(getContext(), permissions[0]) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), permissions[1]) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(permissions, MAP_CODE);
        }else {
            init();
            client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    curLocation = location;
                    if (mapFragment != null) {
                        mapFragment.getMapAsync(callback);
                    }
                }
            });
        }
    }

    private void init() {
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        client = LocationServices.getFusedLocationProviderClient(getContext());
        marker_id = new HashMap<>();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case MAP_CODE:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    runGoogleMap();
                }else {
                    Toast.makeText(getContext(), "You should allow to access location", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}