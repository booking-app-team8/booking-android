package com.example.bookingapp.activities.home.guest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapp.AccommodationSearchAdapter;
import com.example.bookingapp.R;
import com.example.bookingapp.activities.accommodations.CreateAccommodationActivity;
import com.example.bookingapp.activities.adapters.AccommodationAdapter;
import com.example.bookingapp.models.accommodations.AccommodationSearchRequestDTO;
import com.example.bookingapp.services.IAccommodationService;
import com.example.bookingapp.utils.ApiUtils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestMainActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_guest_main);
        Button btnCreateAppointment = findViewById(R.id.btnCreateAppointment);

        btnCreateAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuestMainActivity.this, CreateAccommodationActivity.class);
                startActivity(intent);
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etStartDate = findViewById(R.id.et_start_date);
        etEndDate = findViewById(R.id.et_end_date);
        btnSearch = findViewById(R.id.btn_search);
        etLocation = findViewById(R.id.et_location);
        etGuests = findViewById(R.id.et_guests);
        listView = findViewById(R.id.listviewAccommodationSearch);
        accommodationSearchRequestDTOS = new ArrayList<>();
        adapter = new AccommodationSearchAdapter(this, accommodationSearchRequestDTOS);
        listView.setAdapter(adapter);

        calendar = Calendar.getInstance();

        // Postavljanje listenera za otvaranje DatePicker-a za start date
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
                    // Otvori Početna
                } else if (id == R.id.nav_account) {
                    // Otvori Nalog
                } else if (id == R.id.nav_reservations) {
                    // Otvori Rezervacije
                } else if (id == R.id.nav_notifications) {
                    // Otvori Notifikacije
                }

                drawerLayout.closeDrawers();
                return true;
            }
        });

        // Pronalaženje ikone filtera
        ImageView ivFilter = findViewById(R.id.iv_filter);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.guest_main_menu, menu);
        return true;
    }

    // Funkcija za otvaranje DatePickerDialog-a
    private void openDatePickerDialog(final EditText editText) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                GuestMainActivity.this,
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
        EditText etMinPrice = dialogView.findViewById(R.id.et_min_price);
        EditText etMaxPrice = dialogView.findViewById(R.id.et_max_price);

        // Dodavanje dugmadi za akciju
        builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Ovdje možeš obraditi unos korisnika i filtriranje
                String amenities = etAmenities.getText().toString();
                int selectedId = rgAccommodationType.getCheckedRadioButtonId();
                RadioButton rbSelected = findViewById(selectedId);
                String accommodationType = rbSelected != null ? rbSelected.getText().toString() : null;
                String minPrice = etMinPrice.getText().toString();
                String maxPrice = etMaxPrice.getText().toString();

                // Primjeri obrade unosa (samo ispis u logu, možeš dodati stvarno filtriranje)
                // Log.d("Filter", "Amenities: " + amenities);
                // Log.d("Filter", "Accommodation Type: " + accommodationType);
                // Log.d("Filter", "Min Price: " + minPrice);
                // Log.d("Filter", "Max Price: " + maxPrice);
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
                    Toast.makeText(GuestMainActivity.this, "No accommodations found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<AccommodationSearchRequestDTO>> call, Throwable t) {
                // Rukovanje greškama
                Toast.makeText(GuestMainActivity.this, "Failed to fetch accommodations", Toast.LENGTH_SHORT).show();
            }
        });
    }



}