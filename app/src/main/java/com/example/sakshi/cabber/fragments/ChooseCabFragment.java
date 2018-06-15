package com.example.sakshi.cabber.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sakshi.cabber.R;
import com.example.sakshi.cabber.adapters.ChooseCabAdapter;
import com.example.sakshi.cabber.models.ModelCar;

import java.util.ArrayList;
import java.util.List;

public class ChooseCabFragment extends Fragment {

    private RecyclerView recycler_view;
    private List<ModelCar> cab_list = new ArrayList<>();
    private ChooseCabAdapter adapter;

    public ChooseCabFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choosecab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        adapter = new ChooseCabAdapter(cab_list, getActivity());
        Log.e("Fragment", "Adapter initialized  " + cab_list.size());

        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(adapter);
        prepare_cab_data();
        Log.e("Fragment", "prepare cab data initialized  " + cab_list.size());

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

}
