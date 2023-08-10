package com.example.projectweatherapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectweatherapp.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WewatherAdapter extends RecyclerView.Adapter<WewatherAdapter.WeatherViewHolder> {



    private Context context;
    private ArrayList<WeatherRVModel> weatherRVModelArrayList;

    public WewatherAdapter(Context context, ArrayList<WeatherRVModel> weatherRVModelArrayList) {
        this.context = context;
        this.weatherRVModelArrayList = weatherRVModelArrayList;
    }


    @NonNull
    @Override
    public WewatherAdapter.WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycleview_item,parent,false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WewatherAdapter.WeatherViewHolder holder, int position) {
        WeatherRVModel model = weatherRVModelArrayList.get(position);
        holder.temperatureTV.setText(model.getTemperature()+"c");
        holder.windTV.setText(model.getWindSpeed()+"Km/h");
        Picasso.get().load("https:".concat(model.getIcon())).into(holder.conditionTV);
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        SimpleDateFormat output = new SimpleDateFormat("hh:mm aa");
        try {
            Date t = input.parse(model.getTime());
            holder.timeTV.setText(output.format(t));
        }catch (ParseException e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return  weatherRVModelArrayList.size();
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder {

        private TextView windTV,temperatureTV,timeTV;
        private ImageView conditionTV;
        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);

            windTV =itemView.findViewById(R.id.idTVWindSpeed);
            temperatureTV =itemView.findViewById(R.id.idTVTemperature);
            timeTV =itemView.findViewById(R.id.idTVTime);
            conditionTV =itemView.findViewById(R.id.idIVCondition);
        }
    }
}
