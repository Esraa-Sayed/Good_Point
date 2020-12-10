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
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng curLatLng = new LatLng(curLocation.getLatitude(), curLocation.getLongitude());
            LatLng neighbourLatLng = new LatLng(curLocation.getLatitude()+0.1, curLocation.getLongitude()+0.1);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLatLng,15));
            googleMap.addMarker(new MarkerOptions().position(curLatLng).title("My location"));
            googleMap.addMarker(new MarkerOptions().position(neighbourLatLng));
        }
    };
    String []permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

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
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        client = LocationServices.getFusedLocationProviderClient(getContext());
        if (ActivityCompat.checkSelfPermission(
                getContext(), permissions[0]) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                    getContext(), permissions[1]) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            ActivityCompat.requestPermissions(getActivity(), permissions, MAP_CODE);
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch(requestCode){
            case MAP_CODE:
                if (ActivityCompat.checkSelfPermission(
                        getContext(), permissions[0]) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(
                                getContext(), permissions[1]) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(), permissions, MAP_CODE);
                    return;
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
                break;
        }
    }
}