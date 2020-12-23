package com.helloworld.goodpoint.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.helloworld.goodpoint.R;

public class MapFragment extends Fragment {

    private static final int MAP_CODE = 1;
    SupportMapFragment mapFragment;
    FusedLocationProviderClient client;
    Location curLocation;
    String []permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng curLatLng = new LatLng(curLocation.getLatitude(), curLocation.getLongitude());
            LatLng neighbourLatLng = new LatLng(curLocation.getLatitude()+0.1, curLocation.getLongitude()+0.1);

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLatLng,15));

            googleMap.addMarker(new MarkerOptions().position(curLatLng).title("My location"));
            googleMap.addMarker(new MarkerOptions().position(neighbourLatLng));
        }
    };

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

        init();
        runGoogleMap();

    }

    private void runGoogleMap() {
        if (ActivityCompat.checkSelfPermission(getContext(), permissions[0]) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), permissions[1]) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), permissions, MAP_CODE);
        }
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

    private void init() {
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        client = LocationServices.getFusedLocationProviderClient(getContext());
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch(requestCode){
            case MAP_CODE:
                if (ActivityCompat.checkSelfPermission(
                        getContext(), permissions[0]) == PackageManager.PERMISSION_DENIED ||
                        ActivityCompat.checkSelfPermission(
                                getContext(), permissions[1]) == PackageManager.PERMISSION_DENIED){

                    Toast.makeText(getContext(), "You should allow to access location", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(getActivity(), permissions, MAP_CODE);
                }else {
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
                break;
        }
    }
}