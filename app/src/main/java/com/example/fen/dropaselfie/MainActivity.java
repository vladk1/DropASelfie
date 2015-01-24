package com.example.fen.dropaselfie;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session;
import com.example.fen.dropaselfie.AuthCalls.AsyncSaveImage;
import com.example.fen.dropaselfie.Fragments.SelfiesStickyListFragment;
import com.example.fen.dropaselfie.Utils.LocationUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;


public class MainActivity extends ActionBarActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;
    final static private String APP_KEY = "ob6ytttkzd1gcpc"; //db-ob6ytttkzd1gcpc
    final static private String APP_SECRET = "6jxkg4k0je4ti0f";
    final static private Session.AccessType ACCESS_TYPE = Session.AccessType.DROPBOX;

    /**
     * The {@link android.support.v4.view.ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    private DropboxAPI<AndroidAuthSession> mDBApi;
    private String accessToken;
    private SelfiesStickyListFragment childFragment;
    private Location loc;
    private boolean startAsyncAfterGeo = false;
    private Bitmap imageBitmap;
    private LocationManager mlocManager;
    private LocationListener mlocListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main_tab);
        getSupportActionBar().setElevation(0);

        AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
        AndroidAuthSession session = new AndroidAuthSession(appKeys, ACCESS_TYPE);
        mDBApi = new DropboxAPI<AndroidAuthSession>(session);

        mDBApi.getSession().setOAuth2AccessToken("7lODRiEn_cAAAAAAAAAEYXPCxAR_242Wr6vT6QoNIR5fxyNmOyg48M9H9yuARcO1");

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(mViewPager);

        mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mlocListener = new MyLocationListener(this);
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 1, mlocListener);
        this.loc = new Location("");
//        51.526565, -0.087218
        this.loc.setLatitude(51.526565);
        this.loc.setLongitude(-0.087218);



// And later in some initialization function:
//        AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
//        AndroidAuthSession session = new AndroidAuthSession(appKeys, ACCESS_TYPE);
//        mDBApi = new DropboxAPI<AndroidAuthSession>(session);

        // MyActivity below should be your activity class name
//        mDBApi.getSession().startOAuth2Authentication(MainActivity.this);

//        mDBApi.getSession().setOAuth2AccessToken("7lODRiEn_cAAAAAAAAAEYXPCxAR_242Wr6vT6QoNIR5fxyNmOyg48M9H9yuARcO1");

//        if (mSectionsPagerAdapter.getCurrentFragment() instanceof SelfiesStickyListFragment) {
//            Log.wtf("DbAuthLog", "onResume");
//            childFragment = (SelfiesStickyListFragment) mSectionsPagerAdapter.getCurrentFragment();
//            childFragment.loadSelfies(mDBApi);
//        } else if (mSectionsPagerAdapter.getCurrentFragment() instanceof MapFragment) {
//            Log.wtf("DbAuthLog", "bullshit map");
//        } else {
//            Log.wtf("DbAuthLog", "bullshit");
//        }

    }

    @Override
    protected void onResume() {
        super.onResume();

//        if (mDBApi.getSession().authenticationSuccessful()) {
//            try {
//                // Required to complete auth, sets the access token on the session
//                mDBApi.getSession().finishAuthentication();
//
//                accessToken = mDBApi.getSession().getOAuth2AccessToken();
//
//                Log.wtf("DbAuthLog", "accessToken="+accessToken);
////                7lODRiEn_cAAAAAAAAAEYHBjHJDU4ecGhB3HjjbgSt8HArGdK8M-hpVdLSrOorE-
////                7lODRiEn_cAAAAAAAAAEYXPCxAR_242Wr6vT6QoNIR5fxyNmOyg48M9H9yuARcO1
//
////                mDBApi.getSession().setOAuth2AccessToken();
//
//                if (mSectionsPagerAdapter.currentFragment instanceof SelfiesStickyListFragment) {
//                    Log.wtf("DbAuthLog", "onResume");
//                    childFragment = (SelfiesStickyListFragment) mSectionsPagerAdapter.getCurrentFragment();
//                    childFragment.loadSelfies(mDBApi);
//                } else if (mSectionsPagerAdapter.currentFragment instanceof MapFragment) {
//                    Log.wtf("DbAuthLog", "bullshit map");
//                } else {
//                    Log.wtf("DbAuthLog", "bullshit");
//                }
//
////                mSectionsPagerAdapter.currentFragment.loadSelfies(mDBApi);
//
//            } catch (IllegalStateException e) {
//                Log.i("DbAuthLog", "Error authenticating", e);
//            }
//        }

    }

    public void getLocationCallback(Location loc) {
        this.loc = loc;
        mlocManager.removeUpdates(mlocListener);
        //create a file to write bitmap data

//
//        String filename = LocationUtils.getAddressName(this, loc)+".png";
//        Log.wtf("getLocationCallback", "filename="+filename);
//
//        File file = new File(getApplicationContext().getCacheDir(), filename);
//        try {
//            file.createNewFile();
//            //Convert bitmap to byte array
////            Bitmap bitmap = your bitmap;
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100 /*ignored for PNG*/, bos);
//            byte[] bitmapdata = bos.toByteArray();
//
//            //write the bytes in file
//            FileOutputStream fos = new FileOutputStream(file);
//            fos.write(bitmapdata);
//            fos.close();
//
//            sendToDropboxApi(file, filename);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }

    public void saveToDropBoxCallback(Bitmap imageBitmap) {
//        , "HedyLamarr.png"

        this.imageBitmap = imageBitmap;
        Log.wtf("saveToDropBoxCallback", "imageBitmap="+imageBitmap);

        if (this.loc == null) {

            mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 1, mlocListener);

        } else {

            String filename = LocationUtils.getAddressName(this, this.loc)+".png";
            Log.wtf("getLocationCallback", "filename="+filename);

            File file = new File(getApplicationContext().getCacheDir(), filename);
            try {
                file.createNewFile();
                //Convert bitmap to byte array
//            Bitmap bitmap = your bitmap;
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100 /*ignored for PNG*/, bos);
                byte[] bitmapdata = bos.toByteArray();

                //write the bytes in file
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bitmapdata);
                fos.close();

                sendToDropboxApi(file, filename);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    private void sendToDropboxApi(File imageFile, String imageName) {

        Log.wtf("sendToDropboxApi", "AsyncSaveImage="+imageBitmap);

        new AsyncSaveImage().saveImage(imageFile, imageName, mDBApi);

        SelfiesStickyListFragment childFragment = (SelfiesStickyListFragment) mSectionsPagerAdapter.getCurrentFragment();
        childFragment.loadSelfies(mDBApi);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link android.support.v4.app.FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private Fragment currentFragment;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment = null;

            if (position == 0){

                fragment = new SelfiesStickyListFragment()
                        .newInstance(null);
                currentFragment = fragment;
                childFragment = (SelfiesStickyListFragment) currentFragment;
                childFragment.loadSelfies(mDBApi);
            } else {

                fragment = new MapFragment().newInstance();
            }


            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.Selfies).toUpperCase(l);
                case 1:
                    return getString(R.string.Maps).toUpperCase(l);
            }
            return null;
        }

        public Fragment getCurrentFragment() {
            return currentFragment;
        }
    }


}