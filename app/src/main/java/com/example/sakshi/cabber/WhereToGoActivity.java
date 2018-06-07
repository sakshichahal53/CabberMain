package com.example.sakshi.cabber;

import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;

public class WhereToGoActivity extends FragmentActivity implements OnMapReadyCallback {

    private View statusbar;
    private static final String TAG="WhereToGoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_where_to_go);

        statusbar=findViewById(R.id.status_bar_map);
        CustomStatusBar customStatusBar=new CustomStatusBar(WhereToGoActivity.this,this);
        customStatusBar.setStatusBarColor(statusbar,getResources().getColor(R.color.transparent_black));


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_to_go);
        mapFragment.getMapAsync(this);

        FragmentManager fm = getFragmentManager();
        DialogFragmentReferral dFragment = new DialogFragmentReferral();
        dFragment.show(fm,"Dialog Fragment");

        }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {

            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        googleMap.setMinZoomPreference(15.0f);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28.700939 ,77.272102), 4));

    }
}
