package com.example.projectweatherapp.Fragement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.projectweatherapp.MainActivity;
import com.example.projectweatherapp.R;
import com.example.projectweatherapp.adapter.InteFaceItemClick;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;

public class MapsFragment extends Fragment {

    InteFaceItemClick inteFaceItemClick;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {


            inteFaceItemClick = (InteFaceItemClick) requireActivity();





            LatLng sydney = new LatLng(27.125864900000003,74.77102959999999);
            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(@NonNull LatLng latLng) {
                    googleMap.addMarker(new MarkerOptions().position(latLng).title("joghpur"));


                    Geocoder geocoder = new Geocoder(getContext());

                    try {
                        ArrayList<Address> addresses = (ArrayList<Address>) geocoder.getFromLocation(latLng.latitude, latLng.longitude,1);
                        //  Log.v("Tag",addresses.get(0).getLocality());
                        //Toast.makeText(getContext(),addresses.get(0).getLocality() , Toast.LENGTH_SHORT).show();

                        Log.v("Tag"," City "+addresses.get(0).getLatitude() +" "+addresses.get(0).getLongitude() +""  +addresses.get(0).getAddressLine(1));


                         if (addresses.get(0).getLocality() == null){

                         }else {
                             inteFaceItemClick.clickTheItem(addresses.get(0).getLocality());

                         }







                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            });





        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}