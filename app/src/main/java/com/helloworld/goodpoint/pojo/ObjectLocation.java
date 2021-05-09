package com.helloworld.goodpoint.pojo;

import com.google.android.gms.maps.model.LatLng;

public class ObjectLocation {

    LatLng latLng;
    double longitude;
    double latitude;
    int userId;


    public ObjectLocation(double longitude, double latitude, int userId) {
        latLng = new LatLng(latitude, longitude);
        this.userId = userId;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public int getUserId() {
        return userId;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
