package com.example.sakshi.cabber;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class ChooseCabActivity extends AppCompatActivity implements OnMapReadyCallback {

    private RecyclerView recycler_view;
    private List<ModelCar> cab_list=new ArrayList<>();
    private ChooseCabAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_cab);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        recycler_view= (RecyclerView) findViewById(R.id.recycler_view);



        adapter = new ChooseCabAdapter(cab_list,getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(adapter);

        prepare_cab_data();
    }

    public void prepare_cab_data()
    {
        ModelCar car1=new ModelCar("GoShare","$45","$50",R.drawable.one_inactive);
        cab_list.add(car1);


        ModelCar car2=new ModelCar("GoMeOnly","$65","$80",R.drawable.three_active);
        cab_list.add(car2);


        ModelCar car3=new ModelCar("GoBigger","$85","$95",R.drawable.two_inactive);
        cab_list.add(car3);


        ModelCar car4=new ModelCar("GoFast","$100","$120",R.drawable.four_inactive);
        cab_list.add(car4);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng sydney = new LatLng(28.7041,  77.1025);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

       // 28.7041° N, 77.1025° E

    }
}
