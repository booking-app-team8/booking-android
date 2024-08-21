package com.example.bookingapp.activities.commentsAndGrades;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingapp.R;
import com.example.bookingapp.dtos.UserGetDTO;
import com.example.bookingapp.models.OwnerGrade;
import com.example.bookingapp.models.accommodations.AccommodationDTO;
import com.example.bookingapp.utils.ApiUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
// AccommodationDetailsGradesActivity.java

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingapp.R;
import com.example.bookingapp.models.OwnerGrade;
import com.example.bookingapp.models.accommodations.AccommodationDTO;
import com.example.bookingapp.models.users.User;
import com.example.bookingapp.utils.ApiUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccommodationDetailsGradesActivity extends AppCompatActivity {

    private Long ownerId = 0L;
    private Long accommodationId;
    private List<OwnerGrade> ownerGrades = new ArrayList<>();
    private double averageGrade = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation_details_grades);

        accommodationId = getIntent().getLongExtra("accommodation_data", -1);

        if (accommodationId != -1) {
            fetchAccommodationData(accommodationId);
        }

        Button seeAllButton = findViewById(R.id.seeAllButton);
        seeAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccommodationDetailsGradesActivity.this, OwnerGradesComments.class);
                intent.putExtra("owner_id", ownerId);
                startActivity(intent);
            }
        });
    }

    private void fetchAccommodationData(Long accommodationId) {
        Call<AccommodationDTO> call = ApiUtils.getIAccommodationService().getFullAccommodation(accommodationId);
        call.enqueue(new Callback<AccommodationDTO>() {
            @Override
            public void onResponse(Call<AccommodationDTO> call, Response<AccommodationDTO> response) {
                if (response.isSuccessful()) {
                    AccommodationDTO accommodations = response.body();
                    ownerId = accommodations.getOwnerId();
                    Toast.makeText(AccommodationDetailsGradesActivity.this, "ownerId:" + ownerId, Toast.LENGTH_SHORT).show();
                    fetchOwnerGrades(ownerId);
                    fetchOwnerField(ownerId);
                } else {
                    Log.e("API Error", "Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<AccommodationDTO> call, Throwable t) {
                Log.e("API Failure", t.getMessage());
            }
        });
    }

    private void fetchOwnerField(Long ownerId) {
        Call<UserGetDTO> call = ApiUtils.getUserService().getUsers(ownerId);
        call.enqueue(new Callback<UserGetDTO>() {
            @Override
            public void onResponse(Call<UserGetDTO> call, Response<UserGetDTO> response) {
                if (response.isSuccessful()) {
                    UserGetDTO user = response.body();
                    TextView owner = findViewById(R.id.ownerNameValue);
                    owner.setText(user.getEmail());
                }
            }

            @Override
            public void onFailure(Call<UserGetDTO> call, Throwable t) {

            }
        });

    }

    private void fetchOwnerGrades(Long ownerId) {
        Call<List<OwnerGrade>> call = ApiUtils.getOwnerGradeService().getGradesByOwnerId(ownerId);
        call.enqueue(new Callback<List<OwnerGrade>>() {
            @Override
            public void onResponse(Call<List<OwnerGrade>> call, Response<List<OwnerGrade>> response) {
                if (response.isSuccessful()) {
                    ownerGrades = response.body();
                    calculateAverageGrade();
                } else {
                    Log.e("API Error", "Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<OwnerGrade>> call, Throwable t) {
                Log.e("API Failure", t.getMessage());
            }
        });
    }

    private void calculateAverageGrade() {
        double sum = 0;
        for (OwnerGrade og : ownerGrades) {
            sum += og.getGrade();
        }
        averageGrade = sum / ownerGrades.size();
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedGrade = decimalFormat.format(averageGrade);
        TextView averageGradeValue = findViewById(R.id.averageGradeValue);
        averageGradeValue.setText(formattedGrade);

        Toast.makeText(AccommodationDetailsGradesActivity.this, "size: " + ownerGrades.size(), Toast.LENGTH_SHORT).show();
        Log.d("OwnerGradesComments", "Number of grades received: " + ownerGrades.size());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Nastavljamo", Toast.LENGTH_SHORT).show();
        fetchAccommodationData(accommodationId);
    }
}
