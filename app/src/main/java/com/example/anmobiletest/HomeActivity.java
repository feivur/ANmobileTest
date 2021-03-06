package com.example.anmobiletest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.anmobiletest.database.DataBaseCreate;
import com.example.anmobiletest.database.UserData;
import com.example.anmobiletest.fragments.HomeFragment;
import com.example.anmobiletest.user.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;


public class HomeActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    User user;
    Fragment Home;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataBaseCreate.createDB(getBaseContext());
        user = UserData.getUser();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_menu);
        setupActivity(bottomNavigationView);
        Home = new HomeFragment();
        startServiceAlaramManager();
        loadFragment(Home);

    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fl_content, fragment);
        ft.commit();
    }


    public void startServiceAlaramManager() {
        getApplicationContext().startService(new Intent(getApplicationContext(), ServerChangedService.class));
    }


    @Override
    public void onStart() {
        super.onStart();

    }


}