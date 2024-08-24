package com.example.bookingapp.activities.accommodations;

import static android.app.ProgressDialog.show;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
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
import com.example.bookingapp.services.IAccommodationService;
import com.example.bookingapp.utils.ApiUtils;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class CreateAccommodationActivity extends AppCompatActivity {

    public String name = "";
    public String description = "";
    public Location location = new Location();
    public int minGuests = 0;
    public int maxGuests = 0;
    public int cancellationDaysInAdvance = 0;
    public double defaultedPrice = 0;

    public PriceUnit priceUnit;
    public TypeOfAccommodation typeOfAccommodation;
    public List<Accessories> accessories = new ArrayList<>();
    public List<Photo> images = new ArrayList<>();
    public List<Pricelist> priceLists = new ArrayList<>();

    public List<TimeSlot> timeSlots = new ArrayList<>();
    public List<Uri> photoUris = new ArrayList<>();

    public MultipartBody.Part[] uris;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getMinGuests() {
        return minGuests;
    }

    public void setMinGuests(int minGuests) {
        this.minGuests = minGuests;
    }

    public int getMaxGuests() {
        return maxGuests;
    }

    public void setMaxGuests(int maxGuests) {
        this.maxGuests = maxGuests;
    }

    public int getCancellationDaysInAdvance() {
        return cancellationDaysInAdvance;
    }

    public void setCancellationDaysInAdvance(int cancellationDaysInAdvance) {
        this.cancellationDaysInAdvance = cancellationDaysInAdvance;
    }

    public double getDefaultedPrice() {
        return defaultedPrice;
    }

    public void setDefaultedPrice(double defaultedPrice) {
        this.defaultedPrice = defaultedPrice;
    }

    public PriceUnit getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(PriceUnit priceUnit) {
        this.priceUnit = priceUnit;
    }

    public TypeOfAccommodation getTypeOfAccommodation() {
        return typeOfAccommodation;
    }

    public void setTypeOfAccommodation(TypeOfAccommodation typeOfAccommodation) {
        this.typeOfAccommodation = typeOfAccommodation;
    }

    public List<Accessories> getAccessories() {
        return accessories;
    }

    public List<Photo> getImages() {
        return images;
    }

    public void setImages(List<Photo> images) {
        this.images = images;
    }

    public List<Pricelist> getPriceLists() {
        return priceLists;
    }

    public void setPriceLists(List<Pricelist> priceLists) {
        this.priceLists = priceLists;
    }

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public MultipartBody.Part[] getUris() {
        return uris;
    }

    public void setUris(MultipartBody.Part[] uris) {
        this.uris = uris;
    }

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

    public List<Uri> getPhotoUris() {
        return photoUris;
    }

    public void setPhotoUris(List<Uri> photoUris) {
        this.photoUris = photoUris;
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

    public void setPhotos(List<Photo> photos, MultipartBody.Part[] uris) {
        this.images = photos;
        this.uris = uris;

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

        Call<Long> createResponseCall = ApiUtils.getIAccommodationService().create(accommodationPostDTO);
        createResponseCall.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (response.isSuccessful()) {
                    Long accommodationId = response.body();
                    Toast.makeText(getApplicationContext(), "Accommodation created with ID: " + accommodationId, Toast.LENGTH_LONG).show();
//                  //OVDE SADA IDE UPLOAD SLIKA NA BACK
                    uploadImages(accommodationId);
//

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

        public void uploadImages(Long accommodationId) {
            IAccommodationService service = ApiUtils.getIAccommodationService();
//        MultipartBody.Part[] partsArray = new MultipartBody.Part[this.uris.size()];
//        partsArray = this.uris.toArray(partsArray);
            Call<Void> call = service.uploadImages(accommodationId, this.uris);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(CreateAccommodationActivity.this, "Images uploaded successfully", Toast.LENGTH_SHORT).show();
                        finish();
//                    ((CreateAccommodationActivity) getActivity()).loadTypeFragment();
                    } else {
                        Toast.makeText(CreateAccommodationActivity.this, "Failed to upload images", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    System.out.println(t.getMessage());
                    Toast.makeText(CreateAccommodationActivity.this, "Upload error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }

    @Override
    public void onBackPressed() {
//        Toast.makeText(this, "OnBackPressed", Toast.LENGTH_SHORT).show();
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    }






