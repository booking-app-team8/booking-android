package com.example.bookingapp.activities.accommodations;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.bookingapp.R;
import com.example.bookingapp.dtos.AccommodationPostDTO;
import com.example.bookingapp.dtos.LoginGETDTO;
import com.example.bookingapp.fragments.accommodations.AccessoriesFragment;
import com.example.bookingapp.fragments.accommodations.BasicInfoFragment;
import com.example.bookingapp.fragments.accommodations.LocationFragment;
import com.example.bookingapp.fragments.accommodations.PhotosFragment;
import com.example.bookingapp.fragments.accommodations.PricelistFragment;
import com.example.bookingapp.fragments.accommodations.TimeSlotsFragment;
import com.example.bookingapp.fragments.accommodations.TypeRoomFragment;
import com.example.bookingapp.models.accommodations.Accessories;
import com.example.bookingapp.models.accommodations.Location;
import com.example.bookingapp.models.accommodations.Photo;
import com.example.bookingapp.models.accommodations.PriceUnit;
import com.example.bookingapp.models.accommodations.Pricelist;
import com.example.bookingapp.models.accommodations.TimeSlot;
import com.example.bookingapp.models.accommodations.TypeOfAccommodation;
import com.example.bookingapp.utils.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class CreateAccommodationActivity extends AppCompatActivity {

    public String name;
    public String description;
    public Location location;
    public int minGuests;
    public int maxGuests;
    public int cancellationDaysInAdvance;
    public double defaultedPrice;

    public PriceUnit priceUnit;
    public TypeOfAccommodation typeOfAccommodation;
    public List<Accessories> accessories;
    public List<Photo> images;
    public List<Pricelist> priceLists;

    public List<TimeSlot> timeSlots;

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

    public void setBasicData(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setLocation(String address, String city, String country, double latitude, double longitude) {
        this.location = new Location(address, city, country, longitude, latitude);
    }

    public void setAccessories(List<Accessories> selectedAccessories) {
        this.accessories = selectedAccessories;
    }

    public void setPhotos(List<Photo> photos) {
        this.images = photos;

    }

    public void savePricelistData(List<Pricelist> priceIntervalList) {
        this.priceLists = priceIntervalList;
    }

    public void setTimeSlots(List<TimeSlot> timeSlotList) {
        this.timeSlots = timeSlotList;
    }

    public void setTypeInformation(int minGuestsInt, int maxGuestsInt, int cancellationDaysInt, TypeOfAccommodation valueOf, PriceUnit valueOf1, double defaultedPrice) {
        this.minGuests = minGuestsInt;
        this.maxGuests = maxGuestsInt;
        this.cancellationDaysInAdvance = cancellationDaysInt;
        this.typeOfAccommodation = valueOf;
        this.defaultedPrice = defaultedPrice;
        this.priceUnit = valueOf1;
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

    public void loadTimeSlots() {
        System.out.println(this.name);
        TimeSlotsFragment photosFragment = new TimeSlotsFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, photosFragment)
                .addToBackStack(null)
                .commit();
    }

    public void loadPricelistFragment() {
        System.out.println(this.name);
        PricelistFragment photosFragment = new PricelistFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, photosFragment)
                .addToBackStack(null)
                .commit();
    }

    public void sendAccommodationDataToBackend() {
        AccommodationPostDTO accommodationPostDTO = new AccommodationPostDTO();
        accommodationPostDTO.setName(this.name);
        accommodationPostDTO.setDescription(this.description);
        accommodationPostDTO.setLocation(this.location);
        accommodationPostDTO.setAccessories(this.accessories);
        accommodationPostDTO.setMinGuests(this.minGuests);
        accommodationPostDTO.setMaxGuests(this.maxGuests);
        accommodationPostDTO.setPriceUnit(this.priceUnit);
        accommodationPostDTO.setImages(this.images);
        accommodationPostDTO.setTypeOfAccommodation(this.typeOfAccommodation);
        accommodationPostDTO.setTimeSlots(this.timeSlots);
        accommodationPostDTO.setPriceLists(this.priceLists);
        accommodationPostDTO.setCancellationDaysInAdvance(this.cancellationDaysInAdvance);
        accommodationPostDTO.setDefaultedPrice(this.defaultedPrice);

        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("userEmail", null);
        accommodationPostDTO.setEmail(userEmail);

        Call<Long> createResponseCall = ApiUtils.getAccommodationService().create(accommodationPostDTO);
        createResponseCall.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (response.isSuccessful()) {
                    Long accommodationId = response.body();
                    Toast.makeText(getApplicationContext(), "Accommodation created with ID: " + accommodationId, Toast.LENGTH_LONG).show();
                } else {
                    // Greška sa servera
                    Toast.makeText(getApplicationContext(), "Failed to create accommodation", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                // Greška u komunikaciji sa serverom
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }



}