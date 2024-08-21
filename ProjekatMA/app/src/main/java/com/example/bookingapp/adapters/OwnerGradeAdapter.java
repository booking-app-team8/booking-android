package com.example.bookingapp.adapters;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapp.R;
import com.example.bookingapp.activities.adapters.ReservationAdapter;
import com.example.bookingapp.dtos.UserGetDTO;
import com.example.bookingapp.models.OwnerGrade;
import com.example.bookingapp.models.users.User;
import com.example.bookingapp.utils.ApiUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerGradeAdapter extends RecyclerView.Adapter<OwnerGradeAdapter.ViewHolder> {

    private List<OwnerGrade> ownerGradeList;

    private final OnButtonClickListener buttonClickListener;

    private String userEmail;

    public interface OnButtonClickListener {
        void deleteGrade(OwnerGrade ownerGrade);
    }

    public OwnerGradeAdapter(List<OwnerGrade> ownerGradeList, OnButtonClickListener listener) {
        this.ownerGradeList = ownerGradeList;
        this.buttonClickListener = listener;
    }

    public interface UserCallback {
        void onSuccess(UserGetDTO user);
        void onFailure(Throwable t);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_owner_grade, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OwnerGrade ownerGrade = ownerGradeList.get(position);
        String textG = "Grade: " + String.valueOf(ownerGrade.getGrade());
        holder.tvGrade.setText(textG);
        String textC = "Comment: " + ownerGrade.getComment();
        holder.tvComment.setText(textC);
        String desiredFormat = "dd. MMM yyyy. HH:mm"; // Željeni format vremena
        try {

            SimpleDateFormat originalSdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = originalSdf.parse(ownerGrade.getTime());

            SimpleDateFormat desiredSdf = new SimpleDateFormat(desiredFormat);
            String formattedTimeString = desiredSdf.format(date);
            String text = "Time: " + formattedTimeString;
            holder.tvTime.setText(text);
        } catch (ParseException e) {
            e.printStackTrace();
        }



        fetchUserById(ownerGrade.getGuestId(), new UserCallback() {
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
                    buttonClickListener.deleteGrade(ownerGrade);
                }
            }
        });




    }


    private void fetchUserById(Long guestId, UserCallback callback) {
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
        return ownerGradeList.size();
    }

    public void setOwnerGrades(List<OwnerGrade> ownerGrades) {
        this.ownerGradeList = ownerGrades;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvGrade, tvComment, tvTime, tvUserName;
        Button btnDelete; // Dodaj dugme


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGrade = itemView.findViewById(R.id.tv_grade);
            tvComment = itemView.findViewById(R.id.tv_comment);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvUserName = itemView.findViewById(R.id.tv_user_name);
            btnDelete = itemView.findViewById(R.id.btn_delete);

        }
    }
}

