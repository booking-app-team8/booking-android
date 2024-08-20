package com.example.bookingapp.activities.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bookingapp.R;
import com.example.bookingapp.activities.ApprovalAccommodationRequestActivity;
import com.example.bookingapp.adapters.UserReportsAdapter;
import com.example.bookingapp.dtos.usersDTOs.ReportUserGetDTO;
import com.example.bookingapp.models.AccommodationRequest;
import com.example.bookingapp.models.users.ReportUser;
import com.example.bookingapp.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportedUsersActivity extends AppCompatActivity {

    private ListView listView;
    private UserReportsAdapter adapter;
    private List<ReportUserGetDTO> reportUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reported_users);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        listView = findViewById(R.id.listview);
        reportUsers = new ArrayList<>();
        adapter = new UserReportsAdapter(this, reportUsers);
        listView.setAdapter(adapter);

        loadUserReports();
    }

    private void loadUserReports(){
        ApiUtils.getUserReportsService().getReportRequests().enqueue(new Callback<List<ReportUserGetDTO>>() {

            @Override
            public void onResponse(Call<List<ReportUserGetDTO>> call, Response<List<ReportUserGetDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    reportUsers.clear();
                    reportUsers.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<ReportUserGetDTO>> call, Throwable t) {
                Toast.makeText(ReportedUsersActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}