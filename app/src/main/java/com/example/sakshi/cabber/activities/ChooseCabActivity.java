package com.example.sakshi.cabber.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.sakshi.cabber.adapters.ChooseCabAdapter;
import com.example.sakshi.cabber.models.ModelCar;
import com.example.sakshi.cabber.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;
import java.util.List;

public class ChooseCabActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnPolylineClickListener,
        GoogleMap.OnPolygonClickListener {

    private RecyclerView recycler_view;
    private List<ModelCar> cab_list = new ArrayList<>();
    private ChooseCabAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_choosecab);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);

        adapter = new ChooseCabAdapter(cab_list, getApplicationContext());
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


        //  googleMap=google_map;

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