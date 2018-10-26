package com.verbosetech.caber_native_rider.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.verbosetech.caber_native_rider.fragments.ChooseCabFragment;
import com.verbosetech.caber_native_rider.fragments.CompleteRideInfoFragment;
import com.verbosetech.caber_native_rider.fragments.ReferralFragment;
import com.verbosetech.caber_native_rider.helpers.CustomStatusBar;
import com.verbosetech.caber_native_rider.adapters.PlaceAutocompleteAdapter;
import com.verbosetech.caber_native_rider.helpers.PlaceInfo;
import com.verbosetech.caber_native_rider.R;
import com.verbosetech.caber_native_rider.listener.MainAddFragmentListener;
import com.verbosetech.caber_native_rider.others.SetMarkers;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class WhereToGoActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, MainAddFragmentListener {

    @Override
    public void addFragment(Fragment fragment, boolean addToBackStack, boolean popPrevious) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        if (popPrevious && fm.getBackStackEntryCount() > 0) {
            fm.popBackStackImmediate();
        }
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.mainFrame, fragment, fragment.getClass().getName());
        if (addToBackStack) fragmentTransaction.addToBackStack(fragment.getClass().getName());
        fragmentTransaction.commit();
    }

    public class AutoCompleteTextViewClickListener implements AdapterView.OnItemClickListener {
        AutoCompleteTextView mAutoComplete;
        AdapterView.OnItemClickListener mOriginalListener;

        public AutoCompleteTextViewClickListener(AutoCompleteTextView acTextView, AdapterView.OnItemClickListener originalListener) {
            mAutoComplete = acTextView;
            mOriginalListener = originalListener;
        }

        public void onItemClick(AdapterView<?> adView, View view, int position, long id) {
            mOriginalListener.onItemClick(adView, mAutoComplete, position, id);
        }
    }

    private AutoCompleteTextView tv_auto_complete;           //To location
    private AutoCompleteTextView tv_from_auto_complete;
    private GoogleApiClient google_api_client;

    private Place source_place, destination_place;
    private PlaceInfo source_info, destination_info;
    private PlaceAutocompleteAdapter adapter;
    private static final LatLngBounds BOUNDS_INDIA = new LatLngBounds(new LatLng(23.63936, 68.14712), new LatLng(28.20453, 97.34466));


    private NavigationView navigation_view;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle action_bar_drawer_toggle;

    private GoogleMap google_map;
    private static final String TAG = "WhereToGoActivity";
    private SetMarkers setmarker;
    private Location my_location;

    private FusedLocationProviderClient fused_client;
    private ImageView img_my_location;

    private LatLng source_latlng, destination_latlng, my_location_latlng;

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_where_to_go);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_to_go);
        mapFragment.getMapAsync(this);

        drawerLayout = findViewById(R.id.drawer_layout);
        action_bar_drawer_toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(action_bar_drawer_toggle);
        action_bar_drawer_toggle.syncState();

        final Button btn_nav_bar = findViewById(R.id.btn_nav_bar);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        btn_nav_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "Nav bar button clicked");
                drawer.openDrawer(GravityCompat.START);
            }
        });

        navigation_view = findViewById(R.id.navigation_view);
        navigation_view.getHeaderView(0).findViewById(R.id.actionProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WhereToGoActivity.this, ProfileActivity.class));
            }
        });
        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                int id = item.getItemId();
                Log.e(TAG, "Inside click lisnetet" + id);
                switch (id) {
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


        fused_client = LocationServices.getFusedLocationProviderClient(this);
        img_my_location = findViewById(R.id.img_my_location);


        img_my_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                tv_from_auto_complete.setText("My Location");
                setmarker.moveCamera(new LatLng(my_location.getLatitude(), my_location.getLongitude()), google_map);
                setmarker.set_marker(source_info, destination_info, my_location_latlng, -1, google_map);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!isLocationEnabled(WhereToGoActivity.this)) {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(WhereToGoActivity.this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(WhereToGoActivity.this);
            }
            builder.setTitle("Lcoation Services Disabled")
                    .setMessage("Please Turn Your GPS Location On!")
                    .show();

            if (ContextCompat.checkSelfPermission(WhereToGoActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(WhereToGoActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Please give app permissions in Settings for the app to work!", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
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

        } else {
            google_map.setMyLocationEnabled(true);
            google_map.getUiSettings().setMyLocationButtonEnabled(false);
            fused_client.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        Log.e(TAG, "My Location is " + String.valueOf(location.getLatitude()));
                        my_location = location;
                        my_location_latlng = new LatLng(my_location.getLatitude(), my_location.getLongitude());

                        google_map.setMinZoomPreference(10.0f);
                        google_map.moveCamera(CameraUpdateFactory.newLatLngZoom(my_location_latlng, 5));

                    }
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, "Getting location failed");
                    Toast.makeText(WhereToGoActivity.this, "Getting Location failed", Toast.LENGTH_SHORT).show();
                }
            });

        }

        addFragment(new ReferralFragment(), true, false);

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
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

            if (getSupportFragmentManager().findFragmentByTag(ChooseCabFragment.class.getName()) == null)
                addFragment(new ChooseCabFragment(), true, false);


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

            setmarker.moveCamera(new LatLng(source_place.getViewport().getCenter().latitude, source_place.getViewport().getCenter().longitude), google_map);
            setmarker.set_marker(source_info, destination_info, my_location_latlng, 0, google_map);
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
                    destination_place.getViewport().getCenter().longitude), google_map);

            setmarker.set_marker(source_info, destination_info, my_location_latlng, 1, google_map);

            places.release();
        }
    };

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }


    }
}


