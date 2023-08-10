package com.example.projectweatherapp.Fragement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.projectweatherapp.R;
import com.example.projectweatherapp.RoomDatabase.Note;
import com.example.projectweatherapp.RoomDatabase.NoteDatabase;
import com.example.projectweatherapp.adapter.WeatherRVModel;
import com.example.projectweatherapp.adapter.WewatherAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class MainFragment extends Fragment {

ImageButton saveButton;
ImageView mainImage;
TextView mainTemp,mainCity;

SearchView nikhilSearch;
RecyclerView recyclerView;


// For Adapter
    private ArrayList<WeatherRVModel> weatherRVModelArrayList;
    private WewatherAdapter weatherRVAdapter;

// background service
    Note note;

    NoteDatabase noteDatabase;
    Executor executor;


    public MainFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_main, container, false);


        saveButton = view.findViewById(R.id.saveButton);
        mainImage = view.findViewById(R.id.main_image);
        mainCity = view.findViewById(R.id.main_city);
        mainTemp = view.findViewById(R.id.main_temp);
        recyclerView = view.findViewById(R.id.recycleView);
        nikhilSearch = view.findViewById(R.id.nikhil);


        // Adapter
        weatherRVModelArrayList = new ArrayList<>();
        weatherRVAdapter = new WewatherAdapter(getContext(),weatherRVModelArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        recyclerView.setAdapter(weatherRVAdapter);


        // searchView code
        nikhilSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getWeatherInfo(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            String receivedData = bundle.getString("key");
           // Toast.makeText(getContext(), " "+receivedData, Toast.LENGTH_SHORT).show();
            getWeatherInfo(receivedData);

        }else {
           // Toast.makeText(getContext(), " On Data ", Toast.LENGTH_SHORT).show();
            getWeatherInfo("kuchaman");

        }


        // save the location in room database
        noteDatabase =NoteDatabase.getInstance(getContext());
        executor = Executors.newSingleThreadExecutor();
        saveButton.setOnClickListener(v -> {
            String name = mainCity.getText().toString().trim();
            if (name == ""){
                Toast.makeText(getContext(), "seleted your location", Toast.LENGTH_SHORT).show();
            }else {
                note = new Note(name);
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                            noteDatabase.noteDao().insert(note);
                    }
                });
                Toast.makeText(getContext(), "save the location", Toast.LENGTH_SHORT).show();

            }
        });


        return view;
    }


    private void getWeatherInfo(String city){
        String url = "https://api.weatherapi.com/v1/forecast.json?key=0d440142b47942abb8e164248230506&q=" + city + "&days=1&aqi=yes&alerts=yes";

        //mainCity.setText(city);

        RequestQueue requestQueue  = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                weatherRVModelArrayList.clear();

                try {
                    String temperatur = response.getJSONObject("current").getString("temp_c");
                    String nameOfciity = response.getJSONObject("location").getString("name");
                    mainCity.setText(nameOfciity);
                    mainTemp.setText(temperatur+"℃");
                    int isDay =  response.getJSONObject("current").getInt("is_day");
                    String condition = response.getJSONObject("current").getJSONObject("condition").getString("text");
                    String conditionIcon = response.getJSONObject("current").getJSONObject("condition").getString("icon");
                    Picasso.get().load("https:".concat(conditionIcon)).into(mainImage);
                    if (isDay==1){
                        //morning
//                        Picasso.get().load().into(backIV);
                    }else {
                        //night
//                        Picasso.get().load().into(backIV);
                    }

                    JSONObject forecastObj = response.getJSONObject("forecast");
                    JSONObject forcastO = forecastObj.getJSONArray("forecastday").getJSONObject(0);
                    JSONArray hourArray = forcastO.getJSONArray("hour");


                    for (int i=0;i<hourArray.length(); i++){
                        JSONObject hourObj = hourArray.getJSONObject(i);
                        String time = hourObj.getString("time");
                        String temper = hourObj.getString("temp_c");
                        String img = hourObj.getJSONObject("condition").getString("icon");
                        String wind = hourObj.getString("wind_kph");
                        weatherRVModelArrayList.add(new WeatherRVModel(time,temper,img,"wind"));
                    }

                    weatherRVAdapter.notifyDataSetChanged();



                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Please enter valid city name", Toast.LENGTH_SHORT).show();
                Log.v("Tag",error.toString());
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

}