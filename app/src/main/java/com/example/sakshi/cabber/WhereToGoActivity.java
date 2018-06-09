package com.example.sakshi.cabber;

import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;

public class WhereToGoActivity extends AppCompatActivity implements OnMapReadyCallback {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle action_bar_drawer_toggle;
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


        drawerLayout = findViewById(R.id.drawer_layout);
        action_bar_drawer_toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(action_bar_drawer_toggle);
        action_bar_drawer_toggle.syncState();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);            //REmoving statusbar from Activity
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            FragmentManager fm = getFragmentManager();
            DialogFragmentReferral dFragment = new DialogFragmentReferral();
            dFragment.show(fm, "Dialog Fragment");                   //For adding dialogueFragment
        }

        final Button btn_nav_bar = findViewById(R.id.btn_nav_bar);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        btn_nav_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
                statusbar.setVisibility(View.GONE);
            }
        });
        //NavDrawerImplementation
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            statusbar.setVisibility(View.GONE);
        }


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
