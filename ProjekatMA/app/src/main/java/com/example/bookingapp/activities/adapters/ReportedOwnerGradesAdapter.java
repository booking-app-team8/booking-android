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
import com.example.bookingapp.dtos.grades.OwnerCommentDTO;
import com.example.bookingapp.utils.ApiUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportedOwnerGradesAdapter extends ArrayAdapter<OwnerCommentDTO> {
    private Context context;
    private List<OwnerCommentDTO> ownerCommentDTOS;

    public ReportedOwnerGradesAdapter(Context context, List<OwnerCommentDTO> ownerCommentDTOS){
        super(context, 0, ownerCommentDTOS);
        this.context = context;
        this.ownerCommentDTOS = ownerCommentDTOS;
    }

    @NotNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.card_reported_owner_garde, parent, false);
        }

        OwnerCommentDTO ownerCommentDTO = ownerCommentDTOS.get(position);

        TextView tvOwner = convertView.findViewById(R.id.tv_owner);
        TextView tvGuest = convertView.findViewById(R.id.tv_guest);
        TextView tvGrade = convertView.findViewById(R.id.tv_grade);
        TextView tvComment = convertView.findViewById(R.id.tv_comment);
        Button btnDeleteComment = convertView.findViewById(R.id.btn_delete_comment);

        Long id = ownerCommentDTO.getId();
        String owner = ownerCommentDTO.getOwner().getName() + " " + ownerCommentDTO.getOwner().getSurname();
        String guest = ownerCommentDTO.getGuest().getName() + " " + ownerCommentDTO.getGuest().getSurname();
        String grade = String.valueOf(ownerCommentDTO.getGrade());
        String comment = ownerCommentDTO.getComment();

        tvOwner.setText("Owner: " + owner);
        tvGuest.setText("Guest: " + guest);
        tvGrade.setText("Grade: " + grade);
        tvComment.setText("Comment: " + comment);

        btnDeleteComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiUtils.getGradesService().deleteOwnerGrade(id).enqueue(new Callback<Boolean>() {
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

        return convertView;
    }
}
