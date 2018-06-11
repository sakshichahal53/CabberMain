package com.example.sakshi.cabber;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class WhereToGoActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    private AutoCompleteTextView tv_auto_complete;           //To location
    private AutoCompleteTextView tv_from_auto_complete;
    private GoogleApiClient google_api_client;
    private PlaceInfo m_place;
    private PlaceAutocompleteAdapter adapter;
    private static final LatLngBounds BOUNDS_INDIA =
            new LatLngBounds(new LatLng(23.63936, 68.14712), new LatLng(28.20453, 97.34466));

    private ImageButton btn_show_snippet;
    private Boolean is_snippet_on = true;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle action_bar_drawer_toggle;

    private GoogleMap google_map;
    private Marker m_marker;
    private View statusbar;
    private static final String TAG = "WhereToGoActivity";
    private LinearLayout layout_from_location;

    private RelativeLayout from_location_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_where_to_go);

        statusbar = findViewById(R.id.status_bar_map);
        CustomStatusBar customStatusBar = new CustomStatusBar(WhereToGoActivity.this, this);
        customStatusBar.setStatusBarColor(statusbar, getResources().getColor(R.color.transparent_black));


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_to_go);
        mapFragment.getMapAsync(this);


        drawerLayout = findViewById(R.id.drawer_layout);
        action_bar_drawer_toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(action_bar_drawer_toggle);
        action_bar_drawer_toggle.syncState();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);            //Removing statusbar from Activity
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            FragmentManager fm = getFragmentManager();
            DialogFragmentReferral dFragment = new DialogFragmentReferral();
            dFragment.show(fm, "Dialog Fragment");                             //For adding dialogueFragment
        }

        final Button btn_nav_bar = findViewById(R.id.btn_nav_bar);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        btn_nav_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
                statusbar.setVisibility(View.GONE);
            }
        });                                                                    //NavDrawerImplementation
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            statusbar.setVisibility(View.GONE);
        }

        google_api_client = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();                                                     //initialize google_api_client


        layout_from_location = findViewById(R.id.linear_layout_from);
        //     layout_from_location.setVisibility(View.INVISIBLE);

        tv_auto_complete = findViewById(R.id.to_tv_autocomplete);   // Auto Complete text
        tv_from_auto_complete = findViewById(R.id.from_tv_autocomplete);   //From Location


        adapter = new PlaceAutocompleteAdapter(this, Places.getGeoDataClient(this, null), BOUNDS_INDIA, null);

        tv_auto_complete.setAdapter(adapter);
        tv_from_auto_complete.setAdapter(adapter);

        tv_auto_complete.setOnItemClickListener(mAutocompleteClickListener);
        tv_from_auto_complete.setOnItemClickListener(mAutocompleteClickListener);


        tv_auto_complete.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {

                    //execute our method for searching

                    layout_from_location.setVisibility(View.VISIBLE);
                    geoLocate(tv_auto_complete);
                }

                return false;
            }
        });

        tv_from_auto_complete.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {

                    //execute our method for searching
                    geoLocate(tv_from_auto_complete);
                }

                return false;
            }
        });


        from_location_layout = findViewById(R.id.from_location_marker_layout);
        View vi = getLayoutInflater().inflate(R.layout.from_location_marker, null);

        final TextView tv_snippet = vi.findViewById(R.id.tv_snippet);
        btn_show_snippet = vi.findViewById(R.id.btn_show_snippet);
        btn_show_snippet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (is_snippet_on) {
                    tv_snippet.setVisibility(View.VISIBLE);
                    is_snippet_on = false;
                } else {
                    tv_snippet.setVisibility(View.GONE);
                    is_snippet_on = true;
                }

            }
        });

    }


    private void geoLocate(AutoCompleteTextView view) {
        Log.d(TAG, "geoLocate: geolocating");

        String searchString = view.getText().toString();

        Geocoder geocoder = new Geocoder(WhereToGoActivity.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString, 1);
        } catch (IOException e) {
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage());
        }

        if (list.size() > 0) {
            Address address = list.get(0);

            Log.d(TAG, "geoLocate: found a location: " + address.toString());
            //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();

            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), 10.0f,
                    address.getAddressLine(0));
        }
    }

    private void moveCamera(LatLng latLng, float zoom, String title) {
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        google_map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        //    google_map.setInfoWindowAdapter(new CustomWindowInfoAdapter(WhereToGoActivity.this));

        if (!title.equals("My Location")) {
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            google_map.addMarker(options);
        }

        hideSoftKeyboard();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        google_map = googleMap;

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

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(this, "Give permissions", Toast.LENGTH_SHORT).show();
            return;
        } else
            googleMap.setMyLocationEnabled(true);

        googleMap.setMinZoomPreference(10.0f);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28.700939, 77.272102), 4));


    }


    private void moveCamera(LatLng latLng, float zoom, PlaceInfo placeInfo) {
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        google_map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        google_map.clear();

        google_map.setInfoWindowAdapter(new CustomWindowInfoAdapter(WhereToGoActivity.this));

        if (placeInfo != null) {
            try {
                String snippet = "Address: " + placeInfo.getAddress() + "\n" +
                        "Phone Number: " + placeInfo.getPhoneNumber() + "\n" +
                        "Website: " + placeInfo.getWebsiteUri() + "\n" +
                        "Price Rating: " + placeInfo.getRating() + "\n";

                MarkerOptions options = new MarkerOptions()
                        .position(latLng)
                        .title(placeInfo.getName())
                        .snippet(snippet);
                m_marker = google_map.addMarker(options);

            } catch (NullPointerException e) {
                Log.e(TAG, "moveCamera: NullPointerException: " + e.getMessage());
            }
        } else {
            google_map.addMarker(new MarkerOptions().position(latLng));
        }

        hideSoftKeyboard();
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

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(google_api_client, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.d(TAG, "onResult: Place query did not complete successfully: " + places.getStatus().toString());
                places.release();
                return;
            }
            final Place place = places.get(0);

            try {
                m_place = new PlaceInfo();
                m_place.setName(place.getName().toString());
                Log.d(TAG, "onResult: name: " + place.getName());
                m_place.setAddress(place.getAddress().toString());
                Log.d(TAG, "onResult: address: " + place.getAddress());
//                mPlace.setAttributions(place.getAttributions().toString());
//                Log.d(TAG, "onResult: attributions: " + place.getAttributions());
                m_place.setId(place.getId());
                Log.d(TAG, "onResult: id:" + place.getId());
                m_place.setLatlng(place.getLatLng());
                Log.d(TAG, "onResult: latlng: " + place.getLatLng());
                m_place.setRating(place.getRating());
                Log.d(TAG, "onResult: rating: " + place.getRating());
                m_place.setPhoneNumber(place.getPhoneNumber().toString());
                Log.d(TAG, "onResult: phone number: " + place.getPhoneNumber());
                m_place.setWebsiteUri(place.getWebsiteUri());
                Log.d(TAG, "onResult: website uri: " + place.getWebsiteUri());

                Log.d(TAG, "onResult: place: " + m_place.toString());
            } catch (NullPointerException e) {
                Log.e(TAG, "onResult: NullPointerException: " + e.getMessage());
            }


            moveCamera(new LatLng(place.getViewport().getCenter().latitude,
                    place.getViewport().getCenter().longitude), 10.0f, m_place);


            places.release();
        }
    };
}
