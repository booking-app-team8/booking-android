package com.example.bookingapp.activities.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingapp.R;
import com.example.bookingapp.dtos.grades.AccommodationCommentDTO;
import com.example.bookingapp.dtos.grades.OwnerCommentDTO;
import com.example.bookingapp.utils.ApiUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportedAccommodationGradesAdapter extends ArrayAdapter<AccommodationCommentDTO> {
    private Context context;
    private List<AccommodationCommentDTO> accommodationCommentDTOS;

    public ReportedAccommodationGradesAdapter(Context context, List<AccommodationCommentDTO> accommodationCommentDTOS){
        super(context, 0, accommodationCommentDTOS);
        this.context = context;
        this.accommodationCommentDTOS = accommodationCommentDTOS;
    }

    @NotNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.card_reported_accommodation_garde, parent, false);
        }

        AccommodationCommentDTO accommodationCommentDTO = accommodationCommentDTOS.get(position);

        TextView tvAccommodation = convertView.findViewById(R.id.tv_accommodation);
        TextView tvGuest = convertView.findViewById(R.id.tv_guest);
        TextView tvGrade = convertView.findViewById(R.id.tv_grade);
        TextView tvComment = convertView.findViewById(R.id.tv_comment);
        Button btnDeleteComment = convertView.findViewById(R.id.btn_delete_comment);

        Long id = accommodationCommentDTO.getId();
        String accommodation = accommodationCommentDTO.getAccommodation().getName();
        String guest = accommodationCommentDTO.getGuest().getName() + " " + accommodationCommentDTO.getGuest().getSurname();
        String grade = String.valueOf(accommodationCommentDTO.getGrade());
        String comment = accommodationCommentDTO.getComment();

        tvAccommodation.setText("Accommodation: " + accommodation);
        tvGuest.setText("Guest: " + guest);
        tvGrade.setText("Grade: " + grade);
        tvComment.setText("Comment: " + comment);

        /*
        btnDeleteComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiUtils.getGradesService().deleteAccommodationGrade(id).enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.isSuccessful() && response.body() != null && response.body()) {
                            Toast.makeText(context, "Grade accepted.", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged(); // Osvježava listu
                        } else {
                            Toast.makeText(context, "Failed to accept grade.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Toast.makeText(context, "Error occurred.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
         */

        btnDeleteComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kreiranje dijaloga za potvrdu
                new AlertDialog.Builder(context)
                        .setTitle("Confirm Delete")
                        .setMessage("Are you sure you want to delete this comment?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Pozivanje API metode za brisanje komentara
                                ApiUtils.getGradesService().deleteAccommodationGrade(id).enqueue(new Callback<Boolean>() {
                                    @Override
                                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                        if (response.isSuccessful() && response.body() != null && response.body()) {
                                            Toast.makeText(context, "Grade deleted.", Toast.LENGTH_SHORT).show();
                                            // Osvježavanje liste
                                            loadReportedAccommodationGrades();
                                        } else {
                                            Toast.makeText(context, "Failed to delete grade.", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Boolean> call, Throwable t) {
                                        Toast.makeText(context, "Error occurred.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        return convertView;
    }

    private void loadReportedAccommodationGrades() {
        ApiUtils.getGradesService().getReportedAccommodationGrade().enqueue(new Callback<List<AccommodationCommentDTO>>() {
            @Override
            public void onResponse(Call<List<AccommodationCommentDTO>> call, Response<List<AccommodationCommentDTO>> response) {
                if (response.isSuccessful()) {
                    List<AccommodationCommentDTO> grades = response.body();
                    if (grades != null) {
                        Log.d("ReportedAccommodationGradesAdapter", "Grades loaded successfully, number of grades: " + grades.size());
                        accommodationCommentDTOS.clear(); // Očistimo trenutnu listu
                        accommodationCommentDTOS.addAll(grades); // Dodamo nove ocene
                        notifyDataSetChanged(); // Osvježimo prikaz
                    } else {
                        Log.d("ReportedAccommodationGradesAdapter", "Grades list is null");
                    }
                } else {
                    Toast.makeText(context, "Failed to load grades", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<AccommodationCommentDTO>> call, Throwable t) {
                Log.d("ReportedAccommodationGradesAdapter", "Error: " + t.getMessage());
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
