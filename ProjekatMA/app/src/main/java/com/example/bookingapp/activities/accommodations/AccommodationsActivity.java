package com.example.bookingapp.activities.accommodations;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bookingapp.R;
import com.example.bookingapp.activities.home.host.HostMainActivity;

public class AccommodationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodations);

        Button btnCreateAppointment = findViewById(R.id.btnCreateNewAppointment);

        btnCreateAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccommodationsActivity.this, CreateAccommodationActivity.class);
                startActivity(intent);
            }
        });
    }
}