package com.example.bookingapp.activities.home.host;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.bookingapp.AccommodationSearchAdapter;
import com.example.bookingapp.R;
import com.example.bookingapp.activities.accommodations.CreateAccommodationActivity;
import com.example.bookingapp.activities.adapters.AccommodationAdapter;
import com.example.bookingapp.activities.commentsAndGrades.AccommodationDetailsGradesActivity;
import com.example.bookingapp.activities.home.guest.GuestMainActivity;
import com.example.bookingapp.activities.reservations.HostReservationsActivity;
import com.example.bookingapp.activities.user.User_Account;
import com.example.bookingapp.models.accommodations.Accessories;
import com.example.bookingapp.models.accommodations.AccommodationSearchRequestDTO;
import com.example.bookingapp.models.users.User;
import com.example.bookingapp.services.IAccommodationService;
import com.example.bookingapp.utils.ApiUtils;
import com.example.bookingapp.utils.AuthService;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HostMainActivity extends AppCompatActivity implements AccommodationSearchAdapter.OnShowMoreClickListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private EditText etStartDate, etEndDate, etLocation, etGuests;
    private Calendar calendar;
    public Button btnSearch;
    private ListView listView;
    private AccommodationSearchAdapter adapter;
    private List<AccommodationSearchRequestDTO> accommodationSearchRequestDTOS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_main);

        Toolbar toolbar = findViewById(R.id.toolbar_host);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        checkForRateOwner();

        etStartDate = findViewById(R.id.et_start_date_host);
        etEndDate = findViewById(R.id.et_end_date_host);
        btnSearch = findViewById(R.id.btn_search_host);
        etLocation = findViewById(R.id.et_location_host);
        etGuests = findViewById(R.id.et_guests_host);
        listView = findViewById(R.id.listviewAccommodationSearchHost);
        accommodationSearchRequestDTOS = new ArrayList<>();
        adapter = new AccommodationSearchAdapter(this, accommodationSearchRequestDTOS, this);
        listView.setAdapter(adapter);

        calendar = Calendar.getInstance();

        etStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerDialog(etStartDate);
            }
        });

        // Postavljanje listenera za otvaranje DatePicker-a za end date
        etEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerDialog(etEndDate);
            }
        });

        drawerLayout = findViewById(R.id.drawer_layout_host);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_host_home) {
                    Intent intent = new Intent(HostMainActivity.this, HostMainActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_host_account) {
                    Intent intent = new Intent(HostMainActivity.this, User_Account.class);
                    startActivity(intent);
                } else if (id == R.id.nav_host_accommodations) {
                    // Otvori Rezervacije
                } else if (id == R.id.nav_host_reservations) {
                    Intent intent = new Intent(HostMainActivity.this, HostReservationsActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_host_notifications) {
                    // Otvori Notifikacije
                } else if (id == R.id.nav_create_accommodation) {
                    Intent intent = new Intent(HostMainActivity.this, CreateAccommodationActivity.class);
                    startActivity(intent);
                }

                drawerLayout.closeDrawers();
                return true;
            }
        });

        // Pronalaženje ikone filtera
        ImageView ivFilter = findViewById(R.id.iv_filter_host);
        ivFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterDialog();
            }
        });

        btnSearch.setOnClickListener(v -> {
            String location = etLocation.getText().toString();
            String guestsStr = etGuests.getText().toString();
            Integer guests = guestsStr.isEmpty() ? null : Integer.parseInt(guestsStr);
            String startDate = etStartDate.getText().toString();
            String endDate = etEndDate.getText().toString();

            // Pozivanje API-ja
            searchAccommodations(location, guests, startDate, endDate);
        });

    }

    @Override
    public void onShowMoreClick(AccommodationSearchRequestDTO accommodation) {
        // Navigate to the desired activity
        Toast.makeText(this, "Show more clicked for: " + accommodation.getName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, AccommodationDetailsGradesActivity.class);
//        Toast.makeText(this, "accommodationID:" + accommodation.getName(), Toast.LENGTH_SHORT).show();
        intent.putExtra("accommodation_data", accommodation.getId()); // Pass any needed data
        startActivity(intent);
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.host_main_menu, menu);
        return true;
    }
     */

    private void openDatePickerDialog(final EditText editText) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                HostMainActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Formatiranje i prikaz odabranog datuma u EditText
                        String selectedDate;
                        if((monthOfYear + 1) < 10){
                            selectedDate = year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth;
                        } else{
                            selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        }

                        editText.setText(selectedDate);
                    }
                },
                year, month, day);
        datePickerDialog.show();
    }

    private void showFilterDialog() {
        // Kreiranje dijaloga za filtriranje
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Filter");

        // Učitavanje layout-a za dijalog
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_filter, null);
        builder.setView(dialogView);

        // Pronalazak UI komponenti iz layout-a
        EditText etAmenities = dialogView.findViewById(R.id.et_amenities);
        RadioGroup rgAccommodationType = dialogView.findViewById(R.id.rg_accommodation_type);
        RadioButton rgRoom = dialogView.findViewById(R.id.rb_room);
        RadioButton rgApartment = dialogView.findViewById(R.id.rb_apartment);
        RadioButton rgStudio = dialogView.findViewById(R.id.rb_studio);
        EditText etMinPrice = dialogView.findViewById(R.id.et_min_price);
        EditText etMaxPrice = dialogView.findViewById(R.id.et_max_price);

        // Dodavanje dugmadi za akciju
        builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Flegovi koji ce nam govoriti po kojim parametrima cemo raditi filtriranje
                boolean amenitiesFleg = false;
                boolean typeOfAccommodationFleg = false;
                boolean minPriceFleg = false;
                boolean maxPriceFleg = false;

                // Pogodnosti po kojima korisnik zeli da filtrira
                String amenities = etAmenities.getText().toString();
                Log.d("Filter", "Amenities: " + amenities);
                List<String> amenitiesList = new ArrayList<>();
                if (amenities.length() > 0){
                    String[] amenitiesArray = amenities.split(",");

                    for (String amenity : amenitiesArray) {
                        amenitiesList.add(amenity.trim().toUpperCase());
                        Log.d("Filter", amenity.trim());
                    }

                    if(amenitiesList.size() > 0){
                        amenitiesFleg = true;
                    }
                }

                // Tip smestaja po kom korisnik zeli da filtrira

                String accommodationType = "";

                if (rgRoom.isChecked()) {
                    Log.d("Filter", "Accommodation Type: Room");
                    accommodationType = "ROOM";
                    typeOfAccommodationFleg = true;
                } else if (rgApartment.isChecked()) {
                    Log.d("Filter", "Accommodation Type: Apartment");
                    accommodationType = "APARTMENT";
                    typeOfAccommodationFleg = true;
                } else if (rgStudio.isChecked()) {
                    Log.d("Filter", "Accommodation Type: Studio");
                    accommodationType = "STUDIO";
                    typeOfAccommodationFleg = true;
                }

                // Minimalna i maksimalna cena po kojoj korisnik zeli da filtrira
                String minPrice = etMinPrice.getText().toString();
                String maxPrice = etMaxPrice.getText().toString();

                double minPriceDouble = 0;
                double maxPriceDouble = 0;

                if(minPrice.length()>0){
                    minPriceDouble = Double.parseDouble(minPrice);
                    minPriceFleg = true;
                    Log.d("Filter", "Min price: " + minPrice);
                }

                if(maxPrice.length()>0){
                    maxPriceDouble = Double.parseDouble(maxPrice);
                    maxPriceFleg = true;
                    Log.d("Filter", "Max price: " + maxPrice);
                }

                // Vrsimo filtriranje

                if (amenitiesFleg){
                    // Filtriranje po pogodnostima

                    Iterator<AccommodationSearchRequestDTO> iterator = accommodationSearchRequestDTOS.iterator();
                    while (iterator.hasNext()) {
                        AccommodationSearchRequestDTO accommodationSearchRequestDTO = iterator.next();
                        boolean isOk = true;

                        List<String> accommodationAmmenities = new ArrayList<>();

                        for (Accessories accessories : accommodationSearchRequestDTO.getAccessories()) {
                            accommodationAmmenities.add(accessories.getAccessories().toUpperCase());
                        }

                        for (String amenity : amenitiesList) {
                            if (!accommodationAmmenities.contains(amenity)) {
                                isOk = false;
                                break; // Prekida se iteracija čim se nađe prvi nedostajući amenitet
                            }
                        }

                        if (!isOk) {
                            iterator.remove(); // Sigurno uklanjanje elementa pomoću iteratora
                        }
                    }

                    /*
                    for (AccommodationSearchRequestDTO accommodationSearchRequestDTO : accommodationSearchRequestDTOS){
                        boolean isOk = true;

                        List<String> accommodationAmmenities = new ArrayList<>();

                        for (Accessories accessories : accommodationSearchRequestDTO.getAccessories()){
                            accommodationAmmenities.add(accessories.getAccessories().toUpperCase());
                        }

                        for (String amenity : amenitiesList){
                            if (!accommodationAmmenities.contains(amenity)){
                                isOk = false;
                            }
                        }

                        if (!isOk){
                            accommodationSearchRequestDTOS.remove(accommodationSearchRequestDTO);
                        }
                    }
                     */
                }

                if (typeOfAccommodationFleg){
                    // Filtriranje po tipu smestaja

                    Iterator<AccommodationSearchRequestDTO> iterator = accommodationSearchRequestDTOS.iterator();
                    while (iterator.hasNext()) {
                        AccommodationSearchRequestDTO dto = iterator.next();
                        String typeOfAccommodation = dto.getTypeOfAccommodation().toString();
                        if (!typeOfAccommodation.equals(accommodationType)) {
                            iterator.remove();
                        }
                    }

                }

                if (minPriceFleg && maxPriceFleg) {
                    // Filtriranje i po minimalnoj i po maksimalnoj ceni

                    Iterator<AccommodationSearchRequestDTO> iterator = accommodationSearchRequestDTOS.iterator();
                    while (iterator.hasNext()) {
                        AccommodationSearchRequestDTO dto = iterator.next();
                        if ((dto.getTotalPrice() < minPriceDouble) ||
                                (dto.getTotalPrice() > maxPriceDouble)) {
                            iterator.remove();
                        }
                    }
                } else if (minPriceFleg){
                    // Filtriranje po minimalnoj ceni

                    Iterator<AccommodationSearchRequestDTO> iterator = accommodationSearchRequestDTOS.iterator();
                    while (iterator.hasNext()) {
                        AccommodationSearchRequestDTO dto = iterator.next();
                        if (dto.getTotalPrice() < minPriceDouble) {
                            iterator.remove();
                        }
                    }
                } else if (maxPriceFleg){
                    // Filtriranje po maksimalnoj ceni

                    Iterator<AccommodationSearchRequestDTO> iterator = accommodationSearchRequestDTOS.iterator();
                    while (iterator.hasNext()) {
                        AccommodationSearchRequestDTO dto = iterator.next();
                        if (dto.getTotalPrice() > maxPriceDouble) {
                            iterator.remove();
                        }
                    }

                }

                adapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Prikazivanje dijaloga
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void searchAccommodations(String location, Integer guests, String startDate, String endDate) {
        IAccommodationService api = ApiUtils.getAccommodationService();
        Call<List<AccommodationSearchRequestDTO>> call = api.searchAccommodations(guests, location, startDate, endDate);

        call.enqueue(new Callback<List<AccommodationSearchRequestDTO>>() {
            @Override
            public void onResponse(Call<List<AccommodationSearchRequestDTO>> call, Response<List<AccommodationSearchRequestDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<AccommodationSearchRequestDTO> accommodations = response.body();
                    // Rukovanje rezultatima pretrage
                    accommodationSearchRequestDTOS.clear();
                    accommodationSearchRequestDTOS.addAll(accommodations);
                    adapter.notifyDataSetChanged();
                } else {
                    // Rukovanje greškama
                    Toast.makeText(HostMainActivity.this, "No accommodations found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<AccommodationSearchRequestDTO>> call, Throwable t) {
                // Rukovanje greškama
                Toast.makeText(HostMainActivity.this, "Failed to fetch accommodations", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void checkForRateOwner() {
        User user = AuthService.getCurrentUser();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean stop = false;
//                Ride thisRide;
//                try {
//                    if (user != null) {
//                        thisRide = RideService.getPassengerPendingRide(user.getId());
//                        if (thisRide != null) {
//                            stop = true;
//                            setCancelButtonVisible();
//                            findViewById(R.id.fragment_container).setVisibility(View.GONE);
//                            setBackButtonInvisible();
//                        }
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                //ZNACI OVDE NA SVAKE 2 SEKUNDE PROVERI DA LI GA JE NEKO OCENIO NPR SMISLITI KAKO

                if (!stop) {
                    handler.postDelayed(this, 2000);
                }
            }
        }, 2000);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}