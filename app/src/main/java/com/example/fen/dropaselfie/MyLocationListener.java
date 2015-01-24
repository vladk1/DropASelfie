package com.example.fen.dropaselfie;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class MyLocationListener implements LocationListener {

    private final MainActivity callback;

    public MyLocationListener(MainActivity callback) {
        this.callback = callback;

    }

    @Override
    public void onLocationChanged(Location loc)
    {

        Log.wtf("onLocationChanged","onLocationChanged");
        loc.getLatitude();
        loc.getLongitude();

        String Text = "My current location is: " +
                "Latitud = " + loc.getLatitude() +
                "Longitud = " + loc.getLongitude();

        callback.getLocationCallback(loc);

//        Toast.makeText(callback, Text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider)
    {
//        Toast.makeText( getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT ).show();
    }

    @Override
    public void onProviderEnabled(String provider)
    {
//        Toast.makeText( getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {

    }
}