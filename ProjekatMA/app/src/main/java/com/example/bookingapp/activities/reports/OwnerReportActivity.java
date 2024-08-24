package com.example.bookingapp.activities.reports;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bookingapp.R;
import com.example.bookingapp.activities.commentsAndGrades.AccommodationDetailsGradesActivity;
import com.example.bookingapp.activities.commentsAndGrades.OwnerGradesComments;
import com.example.bookingapp.dtos.ReportUserPostDTO;
import com.example.bookingapp.dtos.usersDTOs.ReportUserGetDTO;
import com.example.bookingapp.fragments.reservations.HostReservationsFragment;
import com.example.bookingapp.models.users.ReportUser;
import com.example.bookingapp.utils.ApiUtils;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerReportActivity extends AppCompatActivity {

    String emailTo;
    String emailFrom;
    Long accommodationId;
    private TextInputEditText editTextComment;

    Button submit;

    private List<ReportUserGetDTO> reportUser = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_report);

        emailFrom = getIntent().getStringExtra("owner_email"); //
        emailTo = getIntent().getStringExtra("guest_email");
        accommodationId = getIntent().getLongExtra("accommodation_id",0);

        editTextComment = findViewById(R.id.editTextComment);




        submit = findViewById(R.id.submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                String reason = editTextComment.getText().toString().trim();
                if (reason.length() < 10) {
                    Toast.makeText(OwnerReportActivity.this, "Comment must be at least 10 characters long", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    ReportUserPostDTO reportUserPostDTO = new ReportUserPostDTO(emailFrom, emailTo, accommodationId, reason);
                    Call<Void> call = ApiUtils.getUserService().reportUser(reportUserPostDTO);
                    call.enqueue(new Callback<Void>() {
                        @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(OwnerReportActivity.this, "Successfully reported!", Toast.LENGTH_SHORT).show();

                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            String message = t.getMessage().toString();
                            Toast.makeText(OwnerReportActivity.this, "e:" + message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });


    }


}