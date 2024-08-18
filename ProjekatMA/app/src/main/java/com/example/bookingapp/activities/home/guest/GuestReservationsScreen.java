package com.example.bookingapp.activities.home.guest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.bookingapp.R;
import com.example.bookingapp.activities.user.User_Account;
import com.google.android.material.navigation.NavigationView;

public class GuestReservationsScreen extends AppCompatActivity {


    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_reservations_screen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Povezivanje sa NavigationView
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_home) {
                    Intent intent = new Intent(GuestReservationsScreen.this, GuestMainActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_requests) {
                    Intent intent = new Intent(GuestReservationsScreen.this, GuestReservationsScreen.class);
                    startActivity(intent);
                } else if (id == R.id.nav_reservations) {
                    Intent intent = new Intent(GuestReservationsScreen.this, GuestReservationsScreen.class);
                    startActivity(intent);
                } else if (id == R.id.nav_favourites) {
                    // Otvori Notifikacije
                }

                drawerLayout.closeDrawers();
                return true;
            }
        });



    }
}