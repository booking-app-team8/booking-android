package com.example.bookingapp.activities.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bookingapp.R;
import com.example.bookingapp.dtos.usersDTOs.UserGetDTO;
import com.example.bookingapp.models.grades.OwnerGrade;
import com.example.bookingapp.utils.ApiUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GradesAdapter extends ArrayAdapter<OwnerGrade> {
    private Context context;

    private List<OwnerGrade> grades;

    public GradesAdapter(Context context, List<OwnerGrade> ownerGrades){
        super(context, 0, ownerGrades);
        this.context = context;
        this.grades = ownerGrades;
    }

    @NotNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("GradesAdapter", "getView called for position: " + position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.card_owner_grade, parent, false);
        }

        Log.d("GradesAdapter", "Item at position: " + position);
        if (grades != null && position < grades.size()) {
            OwnerGrade ownerGrade = grades.get(position);
            Log.d("GradesAdapter", "OwnerGrade object: " + ownerGrade);
            Log.d("GradesAdapter", "Guest ID: " + ownerGrade.getGuestId());
            Log.d("GradesAdapter", "Owner ID: " + ownerGrade.getOwnerId());
        } else {
            Log.d("GradesAdapter", "Grades list is null or position is out of bounds");
        }


        OwnerGrade ownerGrade = grades.get(position);

        // Proveri podatke
        Log.d("GradesAdapter", "Provera");
        Log.d("GradesAdapter", "Guest ID: " + ownerGrade.getGuestId());
        Log.d("GradesAdapter", "Owner ID: " + ownerGrade.getOwnerId());

        // Pronađi TextView elemente u layoutu
        TextView tvGuest = convertView.findViewById(R.id.tv_guest);
        TextView tvOwner = convertView.findViewById(R.id.tv_owner);
        TextView tvTime = convertView.findViewById(R.id.tv_time);
        TextView tvReportStatus = convertView.findViewById(R.id.tv_report_status);
        TextView tvAcceptedStatus = convertView.findViewById(R.id.tv_accepted_status);
        TextView tvGrade = convertView.findViewById(R.id.tv_grade);
        TextView tvComment = convertView.findViewById(R.id.tv_comment);

        // Postavi statičke vrednosti
        //tvTime.setText("Time: " + ownerGrade.getTime());
        tvReportStatus.setText("Report status: " + ownerGrade.getReportStatus().toString());
        if(ownerGrade.isAcceptedStatus()){
            tvAcceptedStatus.setText("Accepted status: " + "ACCEPTED");
        } else{
            tvAcceptedStatus.setText("Accepted status: " + "NOT ACCEPTED");
        }
        tvGrade.setText("Grade: " + ownerGrade.getGrade());
        tvComment.setText("Comment: " + ownerGrade.getComment());

        // Retrofit poziv za Guest-a
        ApiUtils.getGuestService().getGuest(ownerGrade.getGuestId()).enqueue(new Callback<UserGetDTO>() {
            @Override
            public void onResponse(Call<UserGetDTO> call, Response<UserGetDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Ažuriraj TextView sa imenom gosta
                    tvGuest.setText("Guest: " + response.body().getName() + " " + response.body().getSurname());
                } else {
                    tvGuest.setText("Guest: Unknown");
                }
            }

            @Override
            public void onFailure(Call<UserGetDTO> call, Throwable t) {
                tvGuest.setText("Guest: Error loading");
            }
        });

        // Retrofit poziv za Owner-a
        ApiUtils.getOwnerService().getOwnerById(ownerGrade.getOwnerId()).enqueue(new Callback<UserGetDTO>() {
            @Override
            public void onResponse(Call<UserGetDTO> call, Response<UserGetDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Ažuriraj TextView sa imenom vlasnika
                    tvOwner.setText("Owner: " + response.body().getName() + " " + response.body().getSurname());
                } else {
                    tvOwner.setText("Owner: Unknown");
                }
            }

            @Override
            public void onFailure(Call<UserGetDTO> call, Throwable t) {
                tvOwner.setText("Owner: Error loading");
            }
        });

        return convertView;
    }

}
