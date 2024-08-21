package com.example.bookingapp.adapters;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapp.R;
import com.example.bookingapp.dtos.UserGetDTO;
import com.example.bookingapp.models.AccommodationGrade;
import com.example.bookingapp.models.OwnerGrade;
import com.example.bookingapp.utils.ApiUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccommodationGradeAdapter extends RecyclerView.Adapter<AccommodationGradeAdapter.ViewHolder> {

    private List<AccommodationGrade> ownerGrades;
    private Context context;
    private final OnButtonClickListener buttonClickListener;

    private String userEmail;

    public interface OnButtonClickListener {
        void deleteGrade(AccommodationGrade accommodationGrade);
    }


    public AccommodationGradeAdapter(Context context, List<AccommodationGrade> ownerGrades, OnButtonClickListener listener) {
        this.context = context;
        this.ownerGrades = ownerGrades;
        this.buttonClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_owner_grade, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AccommodationGrade accommodationGrade = ownerGrades.get(position);

        // Set the grade value
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedGrade = decimalFormat.format(accommodationGrade.getGrade());
        holder.gradeValue.setText("Grade:" + formattedGrade);

        // Set the comment if available
        if (accommodationGrade.getComment() != null) {
            holder.commentValue.setText("Comment:" + accommodationGrade.getComment());
        } else {
            holder.commentValue.setText("No comment");
        }
        String desiredFormat = "dd. MMM yyyy. HH:mm"; // Željeni format vremena
        try {

            SimpleDateFormat originalSdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = originalSdf.parse(accommodationGrade.getTime());

            SimpleDateFormat desiredSdf = new SimpleDateFormat(desiredFormat);
            String formattedTimeString = desiredSdf.format(date);
            String text = "Time: " + formattedTimeString;
            holder.tvTime.setText(text);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        fetchUserById(accommodationGrade.getGuestId(), new OwnerGradeAdapter.UserCallback() {
            @Override
            public void onSuccess(UserGetDTO user) {
                // Ažurirajte UI sa podacima o korisniku
                String text = "User: " + user.getEmail();
//                userEmail = user.getEmail();
                holder.tvUserName.setText(text); // Dodajte odgovarajući TextView u XML

            }

            @Override
            public void onFailure(Throwable t) {
                holder.tvUserName.setText("Unknown User"); // Ili neka druga poruka
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonClickListener != null) {
                    buttonClickListener.deleteGrade(accommodationGrade);
                }
            }
        });
    }

    private void fetchUserById(Long guestId, OwnerGradeAdapter.UserCallback callback) {
        Call<UserGetDTO> call = ApiUtils.getUserService().getUsers(guestId);
        call.enqueue(new Callback<UserGetDTO>() {
            @Override
            public void onResponse(Call<UserGetDTO> call, Response<UserGetDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Throwable("Failed to fetch user"));
                }
            }

            @Override
            public void onFailure(Call<UserGetDTO> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ownerGrades.size();
    }

    public void setGrades(List<AccommodationGrade> ownerGrades) {
        this.ownerGrades = ownerGrades;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView gradeValue;
        TextView commentValue;
        TextView tvTime;
        TextView tvUserName;
        Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gradeValue = itemView.findViewById(R.id.tv_grade);
            commentValue = itemView.findViewById(R.id.tv_comment);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvUserName = itemView.findViewById(R.id.tv_user_name);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}

