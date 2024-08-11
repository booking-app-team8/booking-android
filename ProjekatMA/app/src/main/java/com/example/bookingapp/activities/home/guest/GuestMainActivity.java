package com.example.bookingapp.activities.home.guest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bookingapp.R;
import com.example.bookingapp.activities.accommodations.CreateAccommodationActivity;

public class GuestMainActivity extends AppCompatActivity {

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


    }
}