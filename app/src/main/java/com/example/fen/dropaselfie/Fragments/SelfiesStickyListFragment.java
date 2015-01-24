package com.example.fen.dropaselfie.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.example.fen.dropaselfie.Adapters.EventListAdapter;
import com.example.fen.dropaselfie.AuthCalls.AsyncGetAllFiles;
import com.example.fen.dropaselfie.AuthCalls.Selfie;
import com.example.fen.dropaselfie.MainActivity;
import com.example.fen.dropaselfie.R;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class SelfiesStickyListFragment extends Fragment  {
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private StickyListHeadersListView listView;
    private FloatingActionButton fab;
    private ArrayList<Selfie> photosList;
    private EventListAdapter adapter;
    private ListView listView2;

    public static SelfiesStickyListFragment newInstance(String eventsURL) {
        SelfiesStickyListFragment fragment = new SelfiesStickyListFragment();
        Bundle bundle = new Bundle();


        bundle.putString("groupLink", eventsURL);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SelfiesStickyListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selfies_list, container, false);

        photosList = new ArrayList<Selfie>();
        adapter = new EventListAdapter(getActivity().getApplicationContext(), photosList);

        listView = (StickyListHeadersListView) view.findViewById(R.id.list);

        listView2 = (ListView) view.findViewById(R.id.list2);

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.attachToListView(listView2);

        listView.setAdapter(adapter);




        imageView = (ImageView) view.findViewById(R.id.imageView1);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        && resultCode == RESULT_OK
        if (requestCode == CAMERA_REQUEST) {
            Bitmap photoBitmap = (Bitmap) data.getExtras().get("data");

            MainActivity parentActivity = (MainActivity) getActivity();
            parentActivity.saveToDropBoxCallback(photoBitmap);

        }
    }

    public void loadSelfies(DropboxAPI<AndroidAuthSession> mDBApi) {
//        MainActivity parentActivity = (MainActivity) getActivity();


        Log.wtf("SelfiesStickyListFragment", "loadSelfies");
        new AsyncGetAllFiles().load(this, mDBApi);
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public void updateSelfies(List<Selfie> selfieList) {
        photosList.clear();
        for (Selfie selfie : selfieList) {
//            Log.wtf("updateSelfies", selfie.getName()+ " selfie.getBitmap()="+selfie.getBitmap());
            String martinKey = martinGetKey(selfie.getName());
            Log.wtf("updateSelfies", selfie.getName()+ " martinKey="+martinKey);
            selfie.setHeaderName(martinKey);
            photosList.add(selfie);
        }

        adapter.notifyDataSetChanged();
    }


    public int findCharacterIndex(String key, char c){
        for(int i=0;i<key.length();i++){
            if(key.charAt(i)==c)
                return i;
        }
        return -1;
    }
    public String removeSlash(String key){
        for(int i=0;i<key.length();i++){
            if(key.charAt(i)=='/'){
                key = key.substring(i+1, key.length());
                return key;
            }
        }
        return key;
    }

    public String martinGetKey(String key){
        key = removeSlash(key);
        int end = findCharacterIndex(key, '(');
        if(end>-1){
            key = key.substring(0, end);
        }
        key = removeSuffix(key);
        return key;
    }

    public String removeSuffix(String key){
        int end = findCharacterIndex(key, '.');
        if(end>-1)
            return key = key.substring(0, end);
        return key;
    }

    public void imageCallback(Bitmap bmp) {

    }
}
