package com.example.bookingapp.activities.home.guest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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
import com.example.bookingapp.activities.commentsAndGrades.AccommodationDetailsGradesActivity;
import com.example.bookingapp.activities.reservations.GuestReservationsActivity;
import com.example.bookingapp.activities.startup.LogInActivity;
import com.example.bookingapp.activities.user.User_Account;
import com.example.bookingapp.models.accommodations.Accessories;
import com.example.bookingapp.models.accommodations.AccommodationSearchRequestDTO;
import com.example.bookingapp.services.IAccommodationService;
import com.example.bookingapp.utils.ApiUtils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestMainActivity extends AppCompatActivity implements AccommodationSearchAdapter.OnShowMoreClickListener, SensorEventListener

{
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private EditText etStartDate, etEndDate, etLocation, etGuests;
    private Calendar calendar;
    public Button btnSearch;
    private ListView listView;
    private AccommodationSearchAdapter adapter;
    private List<AccommodationSearchRequestDTO> accommodationSearchRequestDTOS;

    private long lastShakeTime = 0;


    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float x, y, z;
    private float lastX, lastY, lastZ;
    private long lastUpdate = 0;
    private static final int SHAKE_THRESHOLD = 800;

    private static final long SHAKE_DEBOUNCE_TIME = 500;

    private int shakeTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);

        etStartDate = findViewById(R.id.et_start_date);
        etEndDate = findViewById(R.id.et_end_date);
        btnSearch = findViewById(R.id.btn_search);
        etLocation = findViewById(R.id.et_location);
        etGuests = findViewById(R.id.et_guests);
        listView = findViewById(R.id.listviewAccommodationSearch);
        accommodationSearchRequestDTOS = new ArrayList<>();
        adapter = new AccommodationSearchAdapter(this, accommodationSearchRequestDTOS, this);
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
                    Intent intent = new Intent(GuestMainActivity.this, GuestMainActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_account) {
                    Intent intent = new Intent(GuestMainActivity.this, User_Account.class);
                    startActivity(intent);
                } else if (id == R.id.nav_reservations) {
                    Intent intent = new Intent(GuestMainActivity.this, GuestReservationsActivity.class);
                    startActivity(intent);
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

            if (location.isEmpty() || guestsStr.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields before searching.", Toast.LENGTH_SHORT).show();
            } else {
                searchAccommodations(location, guests, startDate, endDate);
            }
        });

    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.guest_main_menu, menu);
        return true;
    }
     */

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


    @Override
    public void onShowMoreClick(AccommodationSearchRequestDTO accommodation) {
//        Toast.makeText(this, "accommodationName:" + accommodation.getName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, AccommodationDetailsGradesActivity.class);
//        Toast.makeText(this, "accommodationID:" + accommodation.getName(), Toast.LENGTH_SHORT).show();
        intent.putExtra("accommodation_data", accommodation.getId()); // Pass any needed data
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        long curTime = System.currentTimeMillis();

        // Proveravamo da li je prošlo više od 100ms od poslednje promene
        if ((curTime - lastUpdate) > 100) {
            long diffTime = (curTime - lastUpdate);
            lastUpdate = curTime;

            // Uzimamo vrednosti ubrzanja po osi
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];

            // Izračunavanje brzine promene
            float speed = Math.abs(x + y + z - lastX - lastY - lastZ) / diffTime * 10000;

            // Provera da li brzina prelazi definisani prag i debounce vreme
            if (speed > SHAKE_THRESHOLD && (curTime - lastShakeTime) > SHAKE_DEBOUNCE_TIME) {
                lastShakeTime = curTime; // Ažuriramo poslednje vreme shake-a
                onShakeDetected();
            }

            lastX = x;
            lastY = y;
            lastZ = z;
        }
    }

    private void onShakeDetected() {
        Toast.makeText(this, "Shake Detected", Toast.LENGTH_SHORT).show();
        if (shakeTime == 0) {
            sortData();
        } else {
            sortReverse();
        }
    }

    private void sortData() {
        shakeTime = 1;
        Collections.sort(accommodationSearchRequestDTOS, new Comparator<AccommodationSearchRequestDTO>() {
            @Override
            public int compare(AccommodationSearchRequestDTO o1, AccommodationSearchRequestDTO o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        adapter.notifyDataSetChanged();
    }

    public void sortReverse() {
        shakeTime = 0;
        accommodationSearchRequestDTOS.sort(Comparator.comparing(AccommodationSearchRequestDTO::getName).reversed());
        Collections.sort(accommodationSearchRequestDTOS, new Comparator<AccommodationSearchRequestDTO>() {
            @Override
            public int compare(AccommodationSearchRequestDTO o1, AccommodationSearchRequestDTO o2) {
                return o2.getName().compareTo(o1.getName());
            }
        });
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}