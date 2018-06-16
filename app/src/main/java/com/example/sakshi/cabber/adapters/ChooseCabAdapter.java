package com.example.sakshi.cabber.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sakshi.cabber.models.ModelCar;
import com.example.sakshi.cabber.R;

import java.util.List;

/**
 * Created by sakshi on 3/6/18.
 */
public class ChooseCabAdapter extends RecyclerView.Adapter<ChooseCabAdapter.MyViewHolder> {


    public List<ModelCar> cab_list;
    private Context context;


    public ChooseCabAdapter(List<ModelCar> cab_list, Context context) {
        this.cab_list = cab_list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cab_list_single, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ModelCar cab = cab_list.get(position);
        holder.car_name.setText(cab.getName());
        String net_price = cab.getLower_value().concat("-").concat(cab.getUppervalue());


        holder.price.setText(net_price);
        Glide.with(context).load(cab.getImage())
                .thumbnail(0.5f)
                .into(holder.image_car);

        if (position == 1) holder.tv_cabtime.setVisibility(View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        return cab_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView car_name, price;
        public ImageView image_car;
        public TextView tv_cabtime;

        public MyViewHolder(View view) {
            super(view);

            car_name = view.findViewById(R.id.car_name);
            image_car = view.findViewById(R.id.car_image);
            price = view.findViewById(R.id.car_price);
            tv_cabtime = view.findViewById(R.id.tv_time_to_cab);



        }
    }

}
