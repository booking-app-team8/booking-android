package com.example.bookingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;


import com.example.bookingapp.AccommodationRequestAdapter;
import com.example.bookingapp.R;
import com.example.bookingapp.databinding.ActivityApprovalAccommodationRequestBinding;
import com.example.bookingapp.models.AccommodationRequest;
import com.example.bookingapp.services.AccommodationApiService;
import com.example.bookingapp.utils.ApiClient;

import java.util.List;

import retrofit2.Response;
import retrofit2.Call;
import retrofit2.Callback;

public class ApprovalAccommodationRequestActivity extends AppCompatActivity {

    private ActivityApprovalAccommodationRequestBinding binding;
    private ListView listView;
    private AccommodationRequestAdapter adapter;
    private static final String BASE_URL = "http://localhost:8081/api";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityApprovalAccommodationRequestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        listView = findViewById(R.id.listview);

        // Initialize Retrofit using ApiClient
        AccommodationApiService apiService = ApiClient.getClient(BASE_URL).create(AccommodationApiService.class);
        Call<List<AccommodationRequest>> call = apiService.getPendingAccommodationRequests();

        call.enqueue(new Callback<List<AccommodationRequest>>() {
            @Override
            public void onResponse(Call<List<AccommodationRequest>> call, Response<List<AccommodationRequest>> response) {
                if (response.isSuccessful()) {
                    List<AccommodationRequest> requests = response.body();
                    adapter = new AccommodationRequestAdapter(ApprovalAccommodationRequestActivity.this, requests);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<AccommodationRequest>> call, Throwable t) {
                // Handle failure
            }
        });
    }
}