package com.example.sakshi.cabber;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.sakshi.cabber.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class ChooseCabActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnPolylineClickListener,
        GoogleMap.OnPolygonClickListener {

    private final String TAG="ChooseCabActivity";
    private RecyclerView recycler_view;
    private List<com.example.sakshi.cabber.ModelCar> cab_list = new ArrayList<>();
    private ChooseCabAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_cab);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);

        adapter = new com.example.sakshi.cabber.ChooseCabAdapter(cab_list, getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(adapter);

        prepare_cab_data();
    }

    public void prepare_cab_data() {
        ModelCar car1 = new ModelCar("GoShare", "$45", "$50", R.drawable.one_inactive);
        cab_list.add(car1);


       ModelCar car2 = new ModelCar("GoMeOnly", "$65", "$80", R.drawable.three_active);
        cab_list.add(car2);


        ModelCar car3 = new ModelCar("GoBigger", "$85", "$95", R.drawable.two_inactive);
        cab_list.add(car3);


       ModelCar car4 = new ModelCar("GoFast", "$100", "$120", R.drawable.four_inactive);
        cab_list.add(car4);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        googleMap.clear();

        Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .width(20)
                .color(R.color.colorAccent)
                .add(
                        new LatLng(28.613063, 77.227342),
                        new LatLng(28.613170, 77.224788),
                        new LatLng(28.610581, 77.224642),
                        new LatLng(28.610212, 77.224518),
                        new LatLng(28.609991, 77.224263),
                        new LatLng(28.610052, 77.222691),
                        new LatLng(28.610514, 77.212385),
                        new LatLng(28.610881, 77.212031),
                        new LatLng(28.610796, 77.211645),
                        new LatLng(28.611502, 77.210336)));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28.613063, 77.227342), 2));

        googleMap.setMinZoomPreference(10.0f);
        googleMap.setOnPolylineClickListener(this);
        googleMap.setOnPolygonClickListener(this);



    }


    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());

        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
         
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onPolygonClick(Polygon polygon) {

    }

    @Override
    public void onPolylineClick(Polyline polyline) {

    }
}
