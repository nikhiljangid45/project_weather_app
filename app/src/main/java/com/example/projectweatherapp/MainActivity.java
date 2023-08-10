package com.example.projectweatherapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.projectweatherapp.Fragement.LocationFragment;
import com.example.projectweatherapp.Fragement.MainFragment;
import com.example.projectweatherapp.Fragement.MapsFragment;
import com.example.projectweatherapp.adapter.InteFaceItemClick;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements InteFaceItemClick {


    NavigationView navigationView;
    androidx.appcompat.widget.Toolbar toolbar;
    DrawerLayout drawerLayout;
    private int contentView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        toolbar = findViewById(R.id.tooldar);

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigatioView);

        loadfrage(new MainFragment());


        setTherNavigtion();




        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.opNote) {

                loadfrage(new MainFragment());
            } else if (id == R.id.opHome) {
                loadfrage(new LocationFragment());

            } else {
                loadfrage(new MapsFragment());
            }
            drawerLayout.closeDrawer(GravityCompat.START);

            return true;
        });

    }

    private void setTherNavigtion() {
        ActionBarDrawerToggle toggle;
        toggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.OpenDrawer,
                R.string.CloseDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    public void loadfrage(Fragment fragment) {
        FragmentManager ft = getSupportFragmentManager();
        FragmentTransaction fm = ft.beginTransaction();
        fm.add(R.id.container, fragment);
        fm.commit();
    }


    @Override
    public void clickTheItem(String data) {
       // Toast.makeText(this, "Data "+data, Toast.LENGTH_SHORT).show();
        loadfrage(new MainFragment());


        Bundle bundle = new Bundle();
        bundle.putString("key", data);

        Fragment mainFragement = new MainFragment();

        mainFragement.setArguments(bundle);
        FragmentManager ft = getSupportFragmentManager();
        FragmentTransaction fm = ft.beginTransaction();

        fm.add(R.id.container, mainFragement);
        fm.commit();
        
    }
}