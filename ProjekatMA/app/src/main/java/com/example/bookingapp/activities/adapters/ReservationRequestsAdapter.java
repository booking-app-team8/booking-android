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
import com.example.bookingapp.dtos.reservations.ReservationRequestsGetDTO;
import com.example.bookingapp.models.reservations.Reservation;
import com.example.bookingapp.utils.ApiUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationRequestsAdapter extends ArrayAdapter<ReservationRequestsGetDTO> {
    private Context context;
    private List<ReservationRequestsGetDTO> reservationRequestsGetDTOS;

    public ReservationRequestsAdapter(Context context, List<ReservationRequestsGetDTO> reservationRequestsGetDTOS){
        super(context, 0, reservationRequestsGetDTOS);
        this.context = context;
        this.reservationRequestsGetDTOS = reservationRequestsGetDTOS;
    }

    @NotNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.card_reservation_request, parent, false);
        }

        ReservationRequestsGetDTO reservationRequestsGetDTO = reservationRequestsGetDTOS.get(position);

        TextView tvGuest = convertView.findViewById(R.id.tv_guest_name);
        TextView tvStart = convertView.findViewById(R.id.tv_start_date);
        TextView tvEnd = convertView.findViewById(R.id.tv_end_date);
        TextView tvAccommodation = convertView.findViewById(R.id.tv_accommodation);
        TextView tvNumberOfGuests = convertView.findViewById(R.id.tv_number_of_guests);
        TextView tvTotalPrice = convertView.findViewById(R.id.tv_total_price);
        TextView tvStatus = convertView.findViewById(R.id.tv_status);
        Button btnAccept = convertView.findViewById(R.id.btn_accept);
        Button btnReject = convertView.findViewById(R.id.btn_reject);

        Long id = reservationRequestsGetDTO.getId();
        String guest = reservationRequestsGetDTO.getGuest().getName() + " " + reservationRequestsGetDTO.getGuest().getSurname();
        String start = reservationRequestsGetDTO.getStartDate();
        String end = reservationRequestsGetDTO.getEndDate();
        String accommodation = reservationRequestsGetDTO.getAccommodation().getName();
        String numberOfGustes = String.valueOf(reservationRequestsGetDTO.getNumberOfGuests());
        String totalPrice = String.valueOf(reservationRequestsGetDTO.getTotalPrice());
        String status = reservationRequestsGetDTO.getRequestStatus().toString();

        tvGuest.setText("Guest: " + guest);
        tvStart.setText("Start date: " + start);
        tvEnd.setText("End date: " + end);
        tvAccommodation.setText("Accommodation: " + accommodation);
        tvNumberOfGuests.setText("Number of guests: " + numberOfGustes);
        tvTotalPrice.setText("Total price: " + totalPrice + "RSD");
        tvStatus.setText("Status: " + status);

        if (status.equals("PENDING")){
            btnAccept.setVisibility(View.VISIBLE);
            btnReject.setVisibility(View.VISIBLE);
        } else{
            btnAccept.setVisibility(View.GONE);
            btnReject.setVisibility(View.GONE);
        }

        // Todo: accept

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiUtils.getReservationRequestService().acceptReservationRequest(id).enqueue(new Callback<Reservation>() {
                    @Override
                    public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(context, "Reservation created.", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "Failed to accept request.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Reservation> call, Throwable t) {
                        Toast.makeText(context, "Error occurred.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // Todo: reject
        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiUtils.getReservationRequestService().rejectReservationRequest(id).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(context, "Request rejected.", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "Failed to reject request.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(context, "Error occurred.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return convertView;
    }
}
