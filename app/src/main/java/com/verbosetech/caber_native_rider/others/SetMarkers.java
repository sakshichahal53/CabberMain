package com.verbosetech.caber_native_rider.others;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.verbosetech.caber_native_rider.R;
import com.verbosetech.caber_native_rider.adapters.CustomWindowInfoAdapter;
import com.verbosetech.caber_native_rider.fragments.ChooseCabFragment;
import com.verbosetech.caber_native_rider.helpers.DirectionsJSONParser;
import com.verbosetech.caber_native_rider.helpers.MapDirectionsHelper;
import com.verbosetech.caber_native_rider.helpers.PlaceInfo;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sakshi on 13/6/18.
 */

public class SetMarkers {

    private static final String TAG = "SET_MARKERS";
    private Context context;
    private Marker s_marker, d_marker;
    private GoogleMap google_map_this;
    private ArrayList markerpoints = new ArrayList<>();
    private MapDirectionsHelper directions_helper;

    private PolylineOptions lineOptions = null;
    public Polyline poly_line_var;


    public SetMarkers(Context context) {

        this.context = context;
        directions_helper = new MapDirectionsHelper(context);
        markerpoints = new ArrayList();
    }

    public void set_marker(PlaceInfo source_info, PlaceInfo destination_info, LatLng my_location_latlng, int set_place, GoogleMap google_map) {
        google_map_this = google_map;

        if (set_place == -1) {
            MarkerOptions source_options = new MarkerOptions()
                    .position(my_location_latlng)                         //-1 for my location
                    .title("Your location");


            if (s_marker != null) s_marker.remove();

            s_marker = google_map.addMarker(source_options);
        } else if (set_place == 0) {

            MarkerOptions source_options = new MarkerOptions()
                    .position(source_info.getLatlng())
                    .title(source_info.getName());

            if (s_marker != null)
                s_marker.remove();


            s_marker = google_map.addMarker(source_options);


        } else if (set_place == 1) {

            MarkerOptions destination_options = new MarkerOptions()
                    .position(destination_info.getLatlng())
                    .title(destination_info.getName());

            if (d_marker != null)
                d_marker.remove();


            d_marker = google_map.addMarker(destination_options);

        }

//        if (d_marker != null && s_marker != null)
//            draw_route();
    }


    public void moveCamera(LatLng latLng, GoogleMap google_map) {

        google_map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
        google_map.setInfoWindowAdapter(new CustomWindowInfoAdapter(context));

    }

    public boolean is_source_destination_chosen() {
        if (s_marker != null && d_marker != null)
            return true;

        else return false;
    }

    /*** Code for finding the route
     *
     *  ***/

    private void draw_route() {

        if (poly_line_var != null)
            poly_line_var.remove();

        LatLng origin = new LatLng(s_marker.getPosition().latitude, s_marker.getPosition().longitude);
        LatLng dest = new LatLng(d_marker.getPosition().latitude, d_marker.getPosition().longitude);


        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(origin);
        builder.include(dest);
        LatLngBounds bounds = builder.build();

        int padding = 2; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        google_map_this.animateCamera(cu);

        Log.e(TAG, "source:" + origin + " destination: " + dest);
        String url = directions_helper.getDirectionsUrl(origin, dest);
        DownloadTask downloadTask = new DownloadTask();

        // Start downloading json data from Google Directions API
        Log.e(TAG, "Download Start");
        downloadTask.execute(url);

    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = directions_helper.downloadUrl(url[0]);
            } catch (Exception e) {
                Log.e("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            Log.e(TAG, "PArsser Executing the PArse Task");
            parserTask.execute(result);

        }
    }


    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList points = null;
            Log.e(TAG, "inside Post Execute of the PArser private class");
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList();
                lineOptions = new PolylineOptions();

                List<HashMap<String, String>> path = result.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(15);
                lineOptions.color(R.color.colorAccent);
                lineOptions.geodesic(true);

            }

            poly_line_var = google_map_this.addPolyline(lineOptions);

//
//            if (is_source_destination_chosen() && poly_line_var != null) {
//
//                final Activity activity = (Activity) context;
//
//
//
//                FragmentManager fm = activity.getFragmentManager();
//                android.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
//                fragmentTransaction.replace(R.id.map_fragment, new ChooseCabFragment(), "Choose Cab");
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//
//
//            }
        }
    }


}
