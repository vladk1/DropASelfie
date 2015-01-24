package com.example.fen.dropaselfie.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fen.dropaselfie.AuthCalls.AsyncDownloadImage;
import com.example.fen.dropaselfie.AuthCalls.Selfie;
import com.example.fen.dropaselfie.R;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class EventListAdapter extends ArrayAdapter<Selfie> implements StickyListHeadersAdapter {


    private final Context context;
    private final List<Selfie> selfiesList;

    public EventListAdapter(Context context, List<Selfie> selfiesList) {
        super(context, R.layout.rowlayout, selfiesList);
        this.context = context;
        this.selfiesList = selfiesList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);

            ImageView imageView1 = (ImageView) rowView.findViewById(R.id.imageView1);

            Log.wtf("getView", "selfiesList.get(position).getUrl()=" + selfiesList.get(position).getUrl() + " selfiesList.get(position).getName()=" + selfiesList.get(position).getName());


            Selfie curSelfie = selfiesList.get(position);
            if (curSelfie.getBitmap() != null) {
                imageView1.setImageBitmap(curSelfie.getBitmap());
            } else {
                new AsyncDownloadImage(curSelfie, imageView1, context).load(curSelfie.getUrl());
            }

        return rowView;
    }

    @Override
    public View getHeaderView(int position, View view, ViewGroup parent) {

        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_row_header_light_layout, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.white_theme_date);

        textView.setText(selfiesList.get(position).getHeaderName());

        return rowView;
    }

    @Override
    public long getHeaderId(int position) {
//        return 0;
        return Long.valueOf(selfiesList.get(position).getHeaderId());
    }
}
