package com.example.fen.dropaselfie.AuthCalls;


import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AsyncSaveImage {


    public AsyncSaveImage() {

    }

    public void saveImage(File imageFile, String imageName, DropboxAPI<AndroidAuthSession> mDBApi) {
        new DownloadImage(imageFile, mDBApi).execute(imageName);
    }

    public class DownloadImage extends AsyncTask<String,Void,Bitmap> {

        private final File imageFile;
        private final DropboxAPI<AndroidAuthSession> mDBApi;
        private String imageName;

        public DownloadImage(File imageFile, DropboxAPI<AndroidAuthSession> mDBApi) {
            this.imageFile = imageFile;
            this.mDBApi = mDBApi;
        }

        @Override
        protected Bitmap doInBackground(String... params) {

//            if (isNetworkOnline() == true){

                imageName = params[0];

                FileInputStream inputStream = null; //magnum-opus.txt
                DropboxAPI.Entry response = null;
                try {

                    inputStream = new FileInputStream(imageFile);
                    response = mDBApi.putFile("/"+imageName, inputStream,
                            imageFile.length(), null, null);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (DropboxException e) {
                    e.printStackTrace();
                }

                Log.i("DbExampleLog", "The uploaded file's rev is: " + response.rev);

//            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bmp){



        }

    }


//    public boolean isNetworkOnline() {
//        boolean status=false;
//        try{
//            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo netInfo = cm.getNetworkInfo(0);
//            if (netInfo != null && netInfo.getState()==NetworkInfo.State.CONNECTED) {
//                status= true;
//            }else {
//                netInfo = cm.getNetworkInfo(1);
//                if(netInfo!=null && netInfo.getState()==NetworkInfo.State.CONNECTED)
//                    status= true;
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//            return false;
//        }
//        return status;
//
//    }

}
