package com.example.fen.dropaselfie.Utils;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationUtils {


    public static String getAddressName(Context context, Location loc) {
        Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);
        Log.wtf("getAddressName","call");

        try {
            List<Address> addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);

            if (addresses != null) {
                Address returnedAddress = addresses.get(0);

                return returnedAddress.getAddressLine(0);
//                return returnedAddress.getPostalCode();


//                return strReturnedAddress.toString();
//                myAddress.setText(strReturnedAddress.toString());
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getAddressName(Context context, String postCode) {
        Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);

//        geocoder.get

        return null;
    }
}
