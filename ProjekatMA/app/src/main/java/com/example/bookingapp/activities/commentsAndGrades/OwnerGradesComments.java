package com.example.bookingapp.activities.commentsAndGrades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.bookingapp.R;
import com.example.bookingapp.adapters.OwnerGradeAdapter;
import com.example.bookingapp.dtos.UserGetDTO;
import com.example.bookingapp.models.OwnerGrade;
import com.example.bookingapp.models.users.User;
import com.example.bookingapp.utils.ApiUtils;
import com.example.bookingapp.utils.AuthService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerGradesComments extends AppCompatActivity implements OwnerGradeAdapter.OnButtonClickListener {

    Long ownerId;
    public List<OwnerGrade> ownerGrades = new ArrayList<>();
    private OwnerGradeAdapter ownerGradeAdapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_grades_comments);
        // Retrieve the ownerId from the Intent
        ownerId = getIntent().getLongExtra("owner_id", -1); // Default value if not found is -1

        // You can now use the ownerId in your activity
        Log.d("OwnerGradesComments", "Received ownerId: " + ownerId);
        Toast.makeText(this, "ownerId:" + ownerId, Toast.LENGTH_SHORT).show();
        recyclerView = findViewById(R.id.rv_owner_grades);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ownerGradeAdapter = new OwnerGradeAdapter(ownerGrades, this);
        recyclerView.setAdapter(ownerGradeAdapter);


        getGradesByOwnerId(ownerId);
        Toast.makeText(this, "SSS", Toast.LENGTH_SHORT).show();


    }

    private void getGradesByOwnerId(Long ownerId) {
        // Ensure ownerId is valid
        if (ownerId != null && ownerId != -1) {
            Call<List<OwnerGrade>> call = ApiUtils.getOwnerGradeService().getGradesByOwnerId(ownerId);

            call.enqueue(new Callback<List<OwnerGrade>>() {
                @Override
                public void onResponse(Call<List<OwnerGrade>> call, Response<List<OwnerGrade>> response) {
                    if (response.isSuccessful()) {
                        // Retrieve the list of grades from the response
                        ownerGrades = response.body();
                        ownerGradeAdapter.setOwnerGrades(ownerGrades); // Update the adapter's data
                        ownerGradeAdapter.notifyDataSetChanged();

                        Toast.makeText(OwnerGradesComments.this, "size: " + ownerGrades.size(), Toast.LENGTH_SHORT).show();


                        // Handle the list of grades here
                        Log.d("OwnerGradesComments", "Number of grades received: " + ownerGrades.size());

                        // For example, you can update the UI with the list of grades
                    } else {
                        // Handle the error response
                        Log.e("API Error", "Response code: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<List<OwnerGrade>> call, Throwable t) {
                    // Handle the failure
                    Log.e("API Failure", "Error: " + t.getMessage());
                }
            });
        } else {
            // Handle the invalid ownerId scenario
            Toast.makeText(this, "Invalid ownerId received", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void deleteGrade(OwnerGrade ownerGrade) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("userEmail", null);
        Call<UserGetDTO> currUserResponseCall = ApiUtils.getUserService().getUsers(ownerGrade.getGuestId());
        currUserResponseCall.enqueue(new Callback<UserGetDTO>() {
            @Override
            public void onResponse(Call<UserGetDTO> call, Response<UserGetDTO> response) {
                if (response.isSuccessful()) {
                    UserGetDTO guest = response.body();
                    String email = guest.getEmail();

                    if (userEmail.equals(email)) {
                        ownerGrades.remove(ownerGrade);
                        ownerGradeAdapter.setOwnerGrades(ownerGrades);
                        ownerGradeAdapter.notifyDataSetChanged();
                        Call<Boolean> call2 = ApiUtils.getOwnerGradeService().deleteOwnerGrade(ownerGrade.getId());
                        call2.enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                Toast.makeText(OwnerGradesComments.this, "Deleted successfullyBack", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {

                            }
                        });
//                        Toast.makeText(OwnerGradesComments.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(OwnerGradesComments.this, "You can't delete comment which is not yours!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle the case where the response is not successful
                    Toast.makeText(OwnerGradesComments.this, "Failed to fetch user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserGetDTO> call, Throwable t) {

            }
        });
        //nadjem usera po emailu

//        if (user.getId().equals(ownerGrade.getGuestId())) {
//            Toast.makeText(this, "Moze", Toast.LENGTH_SHORT).show();
//        }else {
//            Toast.makeText(this, "Ne mooze", Toast.LENGTH_SHORT).show();
//        }
//        if (!(userEmail.equals(guestEmail))) {
//            Toast.makeText(this, "You can't delete comment which is not yours!" + ownerGrade.getGuestId(), Toast.LENGTH_SHORT).show();
//        }else {
//            Toast.makeText(this, "Moze", Toast.LENGTH_SHORT).show();
//        }
    }
}