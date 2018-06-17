package com.example.sakshi.cabber.adapters;

import android.graphics.Movie;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sakshi.cabber.R;
import com.example.sakshi.cabber.models.SingleTrip;

import java.util.List;

/**
 * Created by sakshi on 17/6/18.
 */

public class PastTripsAdapter extends RecyclerView.Adapter<PastTripsAdapter.MyViewHolder> {
    private List<SingleTrip> my_trips;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_cash_price, tv_status, tv_time, tv_car_name;
        public ImageView img_trip;

        public MyViewHolder(View view) {
            super(view);
            tv_cash_price = (TextView) view.findViewById(R.id.tv_tv_cash_price);
            tv_time = (TextView) view.findViewById(R.id.tv_mytrip_time);
            tv_car_name = (TextView) view.findViewById(R.id.tv_car_name);
            tv_status = (TextView) view.findViewById(R.id.tv_status);
            img_trip = view.findViewById(R.id.img_trip);
        }
    }

    public PastTripsAdapter(List<SingleTrip> my_trips) {
        this.my_trips = my_trips;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_trip, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SingleTrip movie = my_trips.get(position);
        holder.tv_cash_price.setText(movie.getCash());
        holder.tv_car_name.setText(movie.getCar_name());
        holder.tv_time.setText(movie.getTime());

        if (movie.getStatus())
            holder.tv_status.setText("Paid");
        else
            holder.tv_status.setText("Unpaid");

    }

    @Override
    public int getItemCount() {
        return my_trips.size();
    }
}
