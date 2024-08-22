package com.example.bookingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingapp.R;
import com.example.bookingapp.dtos.usersDTOs.BlockDTO;
import com.example.bookingapp.dtos.usersDTOs.ReportUserGetDTO;
import com.example.bookingapp.utils.ApiUtils;
import com.example.bookingapp.utils.AuthService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserReportsAdapter extends ArrayAdapter<ReportUserGetDTO> {

    private Context context;
    private List<ReportUserGetDTO> reportUserGetDTOList;

    public UserReportsAdapter(Context context, List<ReportUserGetDTO> reportUserGetDTOList){
        super(context, 0, reportUserGetDTOList);
        this.context = context;
        this.reportUserGetDTOList = reportUserGetDTOList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.card_report_user, parent, false);
        }

        ReportUserGetDTO reportUserGetDTO = reportUserGetDTOList.get(position);

        TextView tvReportedUser = convertView.findViewById(R.id.tv_reported_user);
        TextView tvReportFrom = convertView.findViewById(R.id.tv_report_from);
        TextView tvAccommodationName = convertView.findViewById(R.id.tv_accommodation_name);
        TextView tvAccommodationAddress = convertView.findViewById(R.id.tv_accommodation_address);
        TextView tvReason = convertView.findViewById(R.id.tv_reason);

        Button btnBlockUser = convertView.findViewById(R.id.btn_block_user);

        tvReportedUser.setText("Reported user: " + reportUserGetDTO.getTo().getName() + " " + reportUserGetDTO.getTo().getSurname());
        tvReportFrom.setText("Reported from: " + reportUserGetDTO.getFrom().getName() + " " + reportUserGetDTO.getFrom().getSurname());
        tvAccommodationName.setText("Accommodation: " + reportUserGetDTO.getAccommodationGetDTO().getName());
        tvAccommodationAddress.setText("Address: " + reportUserGetDTO.getAccommodationGetDTO().getLocation().getAddress() + ", " + reportUserGetDTO.getAccommodationGetDTO().getLocation().getCity());
        tvReason.setText("Reason: " + reportUserGetDTO.getReason());

        btnBlockUser.setOnClickListener(v -> {
            String email = reportUserGetDTO.getTo().getEmail();
            BlockDTO blockDTO = new BlockDTO(true);

            ApiUtils.getUserReportsService().blockUser(email, blockDTO).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "User blocked", Toast.LENGTH_SHORT).show();
                        reportUserGetDTOList.remove(position);
                        notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(context, "Failed to block user", Toast.LENGTH_SHORT).show();
                }
            });
        });

        return  convertView;
    }
}
