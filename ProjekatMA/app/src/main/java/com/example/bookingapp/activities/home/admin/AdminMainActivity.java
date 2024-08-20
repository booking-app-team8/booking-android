package com.example.bookingapp.activities.home.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bookingapp.R;
import com.example.bookingapp.activities.ApprovalAccommodationRequestActivity;
import com.example.bookingapp.activities.user.ReportedUsersActivity;
import com.example.bookingapp.activities.user.User_Account;

public class AdminMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        // Find buttons by their IDs
        Button btnReportedUsers = findViewById(R.id.btn_reported_users);
        Button btnReportedComments = findViewById(R.id.btn_reported_rates);
        Button btnAccommodationsApproval = findViewById(R.id.btn_approve_accommodations);
        Button btnAccount = findViewById(R.id.btn_user_account);

        // Set click listeners for each button
        btnReportedUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, ReportedUsersActivity.class);
                startActivity(intent);
            }
        });

        /*
        btnReportedComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdministratorMainActivity.this, ReportedCommentsActivity.class);
                startActivity(intent);
            }
        });
         */

        btnAccommodationsApproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, ApprovalAccommodationRequestActivity.class);
                startActivity(intent);
            }
        });

        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, User_Account.class);
                startActivity(intent);
            }
        });
    }
}