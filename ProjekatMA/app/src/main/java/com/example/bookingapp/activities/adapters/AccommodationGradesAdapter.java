package com.example.bookingapp.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingapp.R;
import com.example.bookingapp.dtos.grades.AccommodationCommentDTO;
import com.example.bookingapp.models.accommodations.Accommodation;
import com.example.bookingapp.models.users.User;
import com.example.bookingapp.utils.ApiUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccommodationGradesAdapter extends ArrayAdapter<AccommodationCommentDTO> {

    private Context context;

    private List<AccommodationCommentDTO> accommodationCommentDTOS;

    public AccommodationGradesAdapter(Context context, List<AccommodationCommentDTO> accommodationCommentDTOS){
        super(context, 0, accommodationCommentDTOS);
        this.context = context;
        this.accommodationCommentDTOS = accommodationCommentDTOS;
    }

    @NotNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.card_accommodation_grade, parent, false);
        }

        AccommodationCommentDTO accommodationCommentDTO = accommodationCommentDTOS.get(position);

        User guest = accommodationCommentDTO.getGuest();
        String guestFullName = guest.getName() + " " + guest.getSurname();

        Accommodation accommodation = accommodationCommentDTO.getAccommodation();
        String accommodationName = accommodation.getName();

        TextView tvGuest = convertView.findViewById(R.id.tv_guest);
        TextView tvAccommodation = convertView.findViewById(R.id.tv_accommodation);
        TextView tvTime = convertView.findViewById(R.id.tv_time);
        TextView tvAcceptedStatus = convertView.findViewById(R.id.tv_accepted_status);
        TextView tvGrade = convertView.findViewById(R.id.tv_grade);
        TextView tvComment = convertView.findViewById(R.id.tv_comment);

        tvGuest.setText("Guest: " + guestFullName);
        tvAccommodation.setText("Accommodation: " + accommodationName);
        tvTime.setText("Time: " + accommodationCommentDTO.getTime());
        if (accommodationCommentDTO.isAcceptedStatus()){
            tvAcceptedStatus.setText("ACCEPTED COMMENT");
        } else {
            tvAcceptedStatus.setText("NOT ACCEPTED COMMENT");
        }
        tvGrade.setText("Grade: " + String.valueOf(accommodationCommentDTO.getGrade()));
        tvComment.setText("Comment: " + accommodationCommentDTO.getComment());

        Button btnAccept = convertView.findViewById(R.id.btn_accept_grade);
        //Button btnReject = convertView.findViewById(R.id.btn_reject_grade);

        // Provera da li je komentar prihvaćen
        if (accommodationCommentDTO.isAcceptedStatus()) {
            btnAccept.setVisibility(View.GONE); // Sakrij dugme ako je komentar prihvaćen
        } else {
            btnAccept.setVisibility(View.VISIBLE); // Prikaži dugme ako komentar nije prihvaćen
        }

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiUtils.getGradesService().acceptAccommodationGrade(accommodationCommentDTO.getId()).enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.isSuccessful() && response.body() != null && response.body()) {
                            Toast.makeText(context, "Grade accepted.", Toast.LENGTH_SHORT).show();
                            accommodationCommentDTO.setAcceptedStatus(true); // Ažuriraj status lokalno
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

        return convertView;
    }
}
