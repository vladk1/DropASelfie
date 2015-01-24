package com.example.fen.dropaselfie.AuthCalls;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncDownloadImage {

    private final Context context;
    private final ImageView imageView;
    private final Selfie curSelfie;

    public AsyncDownloadImage(Selfie curSelfie, ImageView eventDetailsHeaderFragment, Context context) {
        this.curSelfie = curSelfie;
        this.context = context;
        this.imageView = eventDetailsHeaderFragment;
    }

    public void load(String imageUrl) {
        new DownloadImage().execute(imageUrl);
    }


    public class DownloadImage extends AsyncTask<String,Void,Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {

            params[0] = params[0].replace("http", "https");
            URL url = null;
            try {
                url = new URL(params[0]);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

//            }

//            Log.wtf("onPostExecute", "really weird");

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bmp){

            if (bmp != null) {
                Log.wtf("onPostExecute", "bmp != null");
                imageView.setImageBitmap(bmp);
                curSelfie.setBitmap(bmp);
            } else {
                Log.wtf("onPostExecute", "bmp == null");
            }


        }

    }

    public boolean isNetworkOnline() {
        boolean status=false;
        try{
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState()==NetworkInfo.State.CONNECTED) {
                status= true;
            }else {
                netInfo = cm.getNetworkInfo(1);
                if(netInfo!=null && netInfo.getState()==NetworkInfo.State.CONNECTED)
                    status= true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return status;

    }


}
