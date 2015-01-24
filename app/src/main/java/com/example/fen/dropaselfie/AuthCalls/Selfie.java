package com.example.fen.dropaselfie.AuthCalls;

import android.graphics.Bitmap;
import android.util.Log;

public class Selfie {

    private Bitmap selfieBitmap;
    private String name;
    private String url;
    private long headerId;
    private String headerName;
    private Bitmap bitmap;

    public Selfie() {

    }

    public Selfie(Bitmap selfieBitmap, String name) {
        this.selfieBitmap = selfieBitmap;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setHeaderId(String headerPostCode) {
        Log.wtf("setHeaderId", "headerPostCode="+headerPostCode);
//        this.headerId = Long.getLong(headerPostCode);
        int asInt = headerPostCode.hashCode();
        long asLong = (long) asInt;

        this.headerId = asLong;
    }


    public long getHeaderId() {
        return headerId;
    }

    public void setHeaderName(String newHeaderName) {
        this.headerName = "'"+newHeaderName.trim()+"'";
        Log.wtf("setHeaderName", "headerName=" + headerName);
        setHeaderId(newHeaderName.trim());
    }


    public String getHeaderName() {
        return headerName;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }


    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
