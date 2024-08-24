package com.example.bookingapp.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.bookingapp.R;
import com.example.bookingapp.models.reservations.ReservationGetFrontDTO;

import java.util.List;

public class OwnerReservationAdapter extends ArrayAdapter<ReservationGetFrontDTO> {
    private Context context;
    private List<ReservationGetFrontDTO> reservations;
    private OnButtonClickListener listener;

    public interface OnButtonClickListener {
        void reportUser(ReservationGetFrontDTO reservationGetFrontDTO);
    }

    public OwnerReservationAdapter(Context context, List<ReservationGetFrontDTO> reservations, OnButtonClickListener listener){
        super(context, 0, reservations);
        this.context = context;
        this.reservations = reservations;
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.card_owner_reservation, parent, false);
        }

        ReservationGetFrontDTO reservation = reservations.get(position);

        TextView tvReservationId = convertView.findViewById(R.id.tv_reservation_id);
        TextView tvAccommodationName = convertView.findViewById(R.id.tv_reservation_accommodation_name);
        TextView tvAccommodationAddress = convertView.findViewById(R.id.tv_reservation_address);
        TextView tvGuestName = convertView.findViewById(R.id.tv_reservation_guest_name);
        TextView tvStartDate = convertView.findViewById(R.id.tv_reservation_start_date);
        TextView tvGuests = convertView.findViewById(R.id.tv_reservation_guests);
        TextView tvEndDate = convertView.findViewById(R.id.tv_reservation_end_date);
        TextView tvTotalPrice = convertView.findViewById(R.id.tv_reservation_total_price);
        TextView tvReservationStatus = convertView.findViewById(R.id.tv_reservation_status);
        Button reportButton = convertView.findViewById(R.id.btn_reservation_report);

        tvReservationId.setText("Reservation ID: " + reservation.getId());
        tvAccommodationName.setText("Accommodation: " + reservation.getAccommodation().getName());
        tvAccommodationAddress.setText("Address: " + reservation.getAccommodation().getLocation().getAddress() + ", " + reservation.getAccommodation().getLocation().getCity());
        tvGuestName.setText("Guest: " + reservation.getGuest().getName() + " " + reservation.getGuest().getSurname());
        tvGuests.setText("Number of guests: " + String.valueOf(reservation.getNumberOfGuests()));
        tvStartDate.setText("Start date: " + reservation.getTimeSlot().getStartDate());
        tvEndDate.setText("End date: " + reservation.getTimeSlot().getEndDate());
        tvTotalPrice.setText("Total price: " + reservation.getTotalPrice());
        tvReservationStatus.setText("Status: " + reservation.getReservationStatus().toString());


        reportButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.reportUser(reservation);
            }
        });

        return convertView;
    }

}
