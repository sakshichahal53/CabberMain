package com.verbosetech.caber_native_rider.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.verbosetech.caber_native_rider.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by sakshi on 11/6/18.
 */

public class CustomWindowInfoAdapter implements GoogleMap.InfoWindowAdapter {

    private final View mwindow;
    private final Context context;

    public CustomWindowInfoAdapter(Context context) {
        this.context = context;
        mwindow = LayoutInflater.from(context).inflate(R.layout.from_location_marker, null);
    }

    private void renderWindowText(Marker marker, View view) {
        String title = marker.getTitle();
        TextView tv_from_marker = view.findViewById(R.id.tv_from_marker);

        if (!title.equals("")) {
            tv_from_marker.setText(title);
        }
//
//        String snippet = marker.getSnippet();
//        TextView tv_snippet = view.findViewById(R.id.
//                tv_snippet);
//
//        if (!snippet.equals("")) {
//            tv_snippet.setText(snippet);
        // }
    }

    @Override
    public View getInfoWindow(Marker marker) {

        renderWindowText(marker, mwindow);
        return mwindow;
    }

    @Override
    public View getInfoContents(Marker marker) {

        renderWindowText(marker, mwindow);
        return mwindow;
    }
}
