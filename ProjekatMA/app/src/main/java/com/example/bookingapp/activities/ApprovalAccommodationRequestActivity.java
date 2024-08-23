package com.example.bookingapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bookingapp.AccommodationRequestAdapter;
import com.example.bookingapp.R;
import com.example.bookingapp.models.AccommodationRequest;
import com.example.bookingapp.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApprovalAccommodationRequestActivity extends AppCompatActivity {

    private ListView listView;
    private AccommodationRequestAdapter adapter;
    private List<AccommodationRequest> accommodationRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_accommodation_request);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        Drawable drawable = getResources().getDrawable(R.drawable.ic_back);

        int width = 28; // širina u pixelima
        int height = 28; // visina u pixelima
        drawable.setBounds(0, 0, width, height);

        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        Drawable scaledDrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, width, height, true));

        toolbar.setNavigationIcon(scaledDrawable);

        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed(); // Ili finish(), zavisno od potreba
        });

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        listView = findViewById(R.id.listview);
        accommodationRequests = new ArrayList<>();
        adapter = new AccommodationRequestAdapter(this, accommodationRequests);
        listView.setAdapter(adapter);

        loadAccommodationRequests();
    }

    private void loadAccommodationRequests() {
        ApiUtils.getAccommodationRequestService().getPendingAccommodationRequests().enqueue(new Callback<List<AccommodationRequest>>() {

            @Override
            public void onResponse(Call<List<AccommodationRequest>> call, Response<List<AccommodationRequest>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (AccommodationRequest accommodationRequest: accommodationRequests){
                        Log.d("AccommodationName", accommodationRequest.getName());

                    }
                    accommodationRequests.clear();
                    accommodationRequests.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<AccommodationRequest>> call, Throwable t) {
                Toast.makeText(ApprovalAccommodationRequestActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}