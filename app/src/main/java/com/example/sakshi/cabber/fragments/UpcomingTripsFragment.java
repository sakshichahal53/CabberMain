package com.example.sakshi.cabber.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sakshi.cabber.R;
import com.example.sakshi.cabber.adapters.PastTripsAdapter;
import com.example.sakshi.cabber.models.SingleTrip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sakshi on 15/6/18.
 */

public class UpcomingTripsFragment extends android.support.v4.app.Fragment {

    private RecyclerView recycler_view;
    private PastTripsAdapter m_adapter;
    private List<SingleTrip> my_trips = new ArrayList<>();

    public UpcomingTripsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_upcoming_trips, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recycler_view = (RecyclerView) view.findViewById(R.id.recyler_view_upcoming_trips);

        m_adapter = new PastTripsAdapter(my_trips);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(m_adapter);

        prepareMovieData();
    }

    public void prepareMovieData() {

        SingleTrip trip1 = new SingleTrip("12:00 am", "Rolls Royce-X", "$100-$110", true);
        my_trips.add(trip1);

        SingleTrip trip2 = new SingleTrip("08:21 am", "Maruti Swift Desire", "$65-$85", true);
        my_trips.add(trip2);

        SingleTrip trip3 = new SingleTrip("09:00 am", "Maruti Nano", "$20-$30", true);
        my_trips.add(trip3);

        SingleTrip trip4 = new SingleTrip("11:26 am", "Maruti Suzuki Alto", "$23-43", true);
        my_trips.add(trip4);
    }
}
