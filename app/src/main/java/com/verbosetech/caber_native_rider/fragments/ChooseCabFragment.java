package com.verbosetech.caber_native_rider.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.verbosetech.caber_native_rider.R;
import com.verbosetech.caber_native_rider.adapters.ChooseCabAdapter;
import com.verbosetech.caber_native_rider.listener.MainAddFragmentListener;
import com.verbosetech.caber_native_rider.models.ModelCar;

import java.util.ArrayList;
import java.util.List;

public class ChooseCabFragment extends Fragment {
    private MainAddFragmentListener fragmentAddListener;

    public ChooseCabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fragmentAddListener = (MainAddFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement MainAddFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentAddListener = null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choosecab, container, false);
        RecyclerView recycler_view = view.findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(new ChooseCabAdapter(prepare_cab_data(), getContext()));

        view.findViewById(R.id.choosecab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fragmentAddListener != null) {
                    fragmentAddListener.addFragment(new ScheduleRideFragment(), true, true);
                }
            }
        });
        return view;
    }

    public ArrayList<ModelCar> prepare_cab_data() {
        ArrayList<ModelCar> cab_list = new ArrayList<>();

        ModelCar car1 = new ModelCar("GoShare", "$45", "$50", R.drawable.one_inactive);
        cab_list.add(car1);

        ModelCar car2 = new ModelCar("GoMeOnly", "$65", "$80", R.drawable.three_active);
        cab_list.add(car2);

        ModelCar car3 = new ModelCar("GoBigger", "$85", "$95", R.drawable.two_inactive);
        cab_list.add(car3);

        ModelCar car4 = new ModelCar("GoFast", "$100", "$120", R.drawable.four_inactive);
        cab_list.add(car4);

        return cab_list;
    }

}
