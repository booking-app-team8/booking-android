package com.example.bookingapp.activities.accommodations;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.bookingapp.R;
import com.example.bookingapp.adapters.PhotoAdapter;
import com.example.bookingapp.fragments.accommodations.AccessoriesFragment;
import com.example.bookingapp.fragments.accommodations.BasicInfoFragment;
import com.example.bookingapp.fragments.accommodations.LocationFragment;
import com.example.bookingapp.fragments.accommodations.PhotosFragment;
import com.example.bookingapp.fragments.accommodations.TypeRoomFragment;

public class CreateAccommodationActivity extends AppCompatActivity {

    public String name;
    public String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_accommodation);

        if (savedInstanceState == null) {
            // Prikazujemo prvi fragment prilikom kreiranja aktivnosti
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new BasicInfoFragment())
                    .commit();
        }


    }

    public void setLocationData(String location, String address) {
        this.name = location;
        this.description = address;
    }

    public void loadLocationFragment() {
        System.out.println(this.name);
        LocationFragment photosFragment = new LocationFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, photosFragment)
                .addToBackStack(null)
                .commit();
    }
    public void loadAccessoriesFragment() {
        System.out.println(this.name);
        AccessoriesFragment photosFragment = new AccessoriesFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, photosFragment)
                .addToBackStack(null)
                .commit();
    }

    public void loadPhotosFragment(){
        System.out.println(this.name);
        PhotosFragment photosFragment = new PhotosFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, photosFragment)
                .addToBackStack(null)
                .commit();
    }

    public void loadTypeFragment() {
        System.out.println(this.name);
        TypeRoomFragment photosFragment = new TypeRoomFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, photosFragment)
                .addToBackStack(null)
                .commit();
    }
}