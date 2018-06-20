package com.example.sakshi.cabber;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;

public class WhereToGoActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {


    public class AutoCompleteTextViewClickListener implements AdapterView.OnItemClickListener {

        AutoCompleteTextView mAutoComplete;
        AdapterView.OnItemClickListener mOriginalListener;

        public AutoCompleteTextViewClickListener(AutoCompleteTextView acTextView,
                                                 AdapterView.OnItemClickListener originalListener) {
            mAutoComplete = acTextView;
            mOriginalListener = originalListener;
        }

        public void onItemClick(AdapterView<?> adView, View view, int position,
                                long id) {
            mOriginalListener.onItemClick(adView, mAutoComplete, position, id);
        }
    }

    private AutoCompleteTextView tv_auto_complete;           //To location
    private AutoCompleteTextView tv_from_auto_complete;
    private GoogleApiClient google_api_client;

    private Place source_place, destination_place;
    private PlaceInfo source_info, destination_info;
    private PlaceAutocompleteAdapter adapter;
    private static final LatLngBounds BOUNDS_INDIA =
            new LatLngBounds(new LatLng(23.63936, 68.14712), new LatLng(28.20453, 97.34466));


    private NavigationView navigation_view;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle action_bar_drawer_toggle;

    private GoogleMap google_map;
    private View statusbar;
    private static final String TAG = "WhereToGoActivity";
    private SetMarkers setmarker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_where_to_go);

        Log.e(TAG, "In OnCreate");
        statusbar = findViewById(R.id.status_bar_map);
        CustomStatusBar customStatusBar = new CustomStatusBar(WhereToGoActivity.this, this);
        customStatusBar.setStatusBarColor(statusbar, getResources().getColor(R.color.transparent_black));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_to_go);
        mapFragment.getMapAsync(this);

        drawerLayout = findViewById(R.id.drawer_layout);
        action_bar_drawer_toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(action_bar_drawer_toggle);
        action_bar_drawer_toggle.syncState();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);            //Removing statusbar from Activity

        FragmentManager fm = getFragmentManager();
        DialogFragmentReferral dFragment = new DialogFragmentReferral();
        dFragment.show(fm, "Dialog Fragment");                             //For adding dialogueFragment

        final Button btn_nav_bar = findViewById(R.id.btn_nav_bar);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        btn_nav_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "Nav bar button clicked");
                drawer.openDrawer(GravityCompat.START);
                statusbar.setVisibility(View.GONE);
            }
        });

        //NavDrawerImplementation
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            statusbar.setVisibility(View.INVISIBLE);
        }

        navigation_view = findViewById(R.id.navigation_view);
        Log.e(TAG, "navigation view found ...id= " + navigation_view.getId());
        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                Log.e(TAG, "Inside click lisnetet" + id);
                switch (id) {

                    case R.id.home_:
                        startActivity(new Intent(WhereToGoActivity.this, ProfileActivity.class));
                        break;

                    case R.id.trips:
                        startActivity(new Intent(WhereToGoActivity.this, MyTripsActivity.class));
                        break;

                    case R.id.payment:
                        startActivity(new Intent(WhereToGoActivity.this, PaymentsActivity.class));
                        break;

                    case R.id.help:
                        startActivity(new Intent(WhereToGoActivity.this, HelpActivity.class));
                        break;

                    case R.id.refer:
                        startActivity(new Intent(WhereToGoActivity.this, ReferNowActivity.class));
                        break;
                    case R.id.logout:
                        startActivity(new Intent(WhereToGoActivity.this, LoginActivity.class));
                        break;

                }

                return true;
            }

        });

        google_api_client = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();                                                     //initialize google_api_client


        tv_auto_complete = findViewById(R.id.to_tv_autocomplete);   // Auto Complete text
        tv_from_auto_complete = findViewById(R.id.from_tv_autocomplete);   //From Location

        source_info = new PlaceInfo();
        destination_info = new PlaceInfo();

        adapter = new PlaceAutocompleteAdapter(this, Places.getGeoDataClient(this, null), BOUNDS_INDIA, null);
        tv_auto_complete.setAdapter(adapter);
        tv_from_auto_complete.setAdapter(adapter);

        tv_auto_complete.setOnItemClickListener(new AutoCompleteTextViewClickListener(tv_auto_complete, mAutocompleteClickListener));
        tv_from_auto_complete.setOnItemClickListener(new AutoCompleteTextViewClickListener(tv_from_auto_complete, mAutocompleteClickListener));

        setmarker = new SetMarkers(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        google_map = googleMap;

        try {

            boolean success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json));
            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Turn your location on!", Toast.LENGTH_SHORT).show();
            return;
        } else
            googleMap.setMyLocationEnabled(true);


        googleMap.setMinZoomPreference(15.0f);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28.700939, 77.272102), 5));


    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    /*
        --------------------------- google places API autocomplete suggestions -----------------
     */

    private void hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            hideSoftKeyboard();

            final AutocompletePrediction item = adapter.getItem(i);
            final String placeId = item.getPlaceId();

            if (view.getId() == R.id.from_tv_autocomplete) {
                Log.e(TAG, "Source Called");
                PendingResult<PlaceBuffer> place_result_source = Places.GeoDataApi.getPlaceById(google_api_client, placeId);
                place_result_source.setResultCallback(source_callback);
            } else if (view.getId() == R.id.to_tv_autocomplete) {
                Log.e(TAG, "Destination Called");
                PendingResult<PlaceBuffer> place_result_destination = Places.GeoDataApi.getPlaceById(google_api_client, placeId);
                place_result_destination.setResultCallback(destination_callback);

            }


        }
    };

    private ResultCallback<PlaceBuffer> source_callback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {

                Log.e(TAG, "Query UnSuccessful");
                places.release();            //query not completed successfully
                return;
            }
            source_place = places.get(0);

            try {
                source_info.setName(source_place.getName().toString());
                source_info.setAddress(source_place.getAddress().toString());
                source_info.setId(source_place.getId());
                source_info.setLatlng(source_place.getLatLng());

            } catch (NullPointerException e) {
                Log.e(TAG, "onResult: NullPointerException: " + e.getMessage());
            }

            Log.e(TAG, "Source is " + source_place.toString());

            setmarker.moveCamera(new LatLng(source_place.getViewport().getCenter().latitude,
                    source_place.getViewport().getCenter().longitude), 15.0f, google_map);

            setmarker.set_marker(source_info, destination_info, 0, google_map);
            places.release();
        }
    };


    private ResultCallback<PlaceBuffer> destination_callback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {

                Log.e(TAG, "Query unsucessful");
                places.release();            //query not completed successfully
                return;
            }

            destination_place = places.get(0);
            try {
                destination_info.setName(destination_place.getName().toString());
                destination_info.setAddress(destination_place.getAddress().toString());
                destination_info.setId(destination_place.getId());
                destination_info.setLatlng(destination_place.getLatLng());

            } catch (NullPointerException e) {
                Log.e(TAG, "onResult: NullPointerException: " + e.getMessage());
            }

            Log.e(TAG, "Destination is " + destination_place.toString());

            setmarker.moveCamera(new LatLng(destination_place.getViewport().getCenter().latitude,
                    destination_place.getViewport().getCenter().longitude), 15.0f, google_map);

            setmarker.set_marker(source_info, destination_info, 1, google_map);

            places.release();
        }
    };

}
