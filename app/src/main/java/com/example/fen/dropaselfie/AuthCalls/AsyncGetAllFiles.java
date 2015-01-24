package com.example.fen.dropaselfie.AuthCalls;


import android.os.AsyncTask;
import android.util.Log;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.example.fen.dropaselfie.Fragments.SelfiesStickyListFragment;

import java.util.ArrayList;
import java.util.List;

public class AsyncGetAllFiles {


    public AsyncGetAllFiles() {

    }


    public void load(SelfiesStickyListFragment callback, DropboxAPI<AndroidAuthSession> mDBApi) {
        new DownloadFiles(callback, mDBApi).execute();
    }

    public class DownloadFiles extends AsyncTask<String, Void, List<Selfie>> {

        private final DropboxAPI<AndroidAuthSession> mDBApi;
        private final SelfiesStickyListFragment callback;
        private DropboxAPI.DropboxLink dbLinkImage;

        public DownloadFiles(SelfiesStickyListFragment callback, DropboxAPI<AndroidAuthSession> mDBApi) {
            this.mDBApi = mDBApi;
            this.callback = callback;
        }



        @Override
        protected List<Selfie> doInBackground(String... params) {

//            if (isNetworkOnline() == true){
            List<Selfie> selfieList = new ArrayList<Selfie>();

            DropboxAPI.Entry entries = null;
            try {
                entries = mDBApi.metadata("", 100, null, true, null);

                for (DropboxAPI.Entry e : entries.contents) {
                    if (!e.isDeleted && e.fileName().contains("png")) {

                        dbLinkImage = mDBApi.media(e.path, false);

////                        URL url = new URL(dbLinkImage.url);
////                        Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//                        Picasso.with(callback.getActivity()).load(dbLinkImage.url).into(target);

                        Selfie newSelfie = new Selfie();
                        newSelfie.setUrl(dbLinkImage.url);
                        newSelfie.setName(e.path);

                        selfieList.add(newSelfie);


                        Log.i("Item File url=", dbLinkImage.url+" name="+e.path);

                    }
                }
                return selfieList;

            } catch (DropboxException e) {
                e.printStackTrace();
            }

//            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Selfie> imageLinks) {
            callback.updateSelfies(imageLinks);
        }





    }





}