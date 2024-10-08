package com.example.bookingapp.activities.adapters;



import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.bookingapp.R;
import com.example.bookingapp.activities.ApprovalAccommodationRequestActivity;
import com.example.bookingapp.activities.startup.LogInActivity;
import com.example.bookingapp.dtos.ReservationGetDTO;
import com.example.bookingapp.models.accommodations.AccommodationSearchRequestDTO;
import com.example.bookingapp.models.enums.ReservationStatus;
import com.example.bookingapp.models.reservations.Reservation;
import com.example.bookingapp.models.reservations.ReservationGetFrontDTO;
import com.example.bookingapp.utils.ApiUtils;


import java.util.List;

import kotlin.jvm.internal.Ref;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationAdapter extends ArrayAdapter<ReservationGetFrontDTO> {


    private final OnButtonClickListener buttonClickListener;
    private Context context;
    private List<ReservationGetFrontDTO> reservations;



    public interface OnButtonClickListener {
        void onRateOwnerClick(Long id, Long guestId);
        void onRateAccommodationClick(Long guestId, Long accommodationId);
//        void onCancelClick(Reservation reservation);
        void onReportClick(ReservationGetFrontDTO reservation);
    }

    private List<AccommodationSearchRequestDTO> accommodationSearchRequestDTOS;

    private Long ownerId;

    public interface OwnerIdCallback {
        void onOwnerIdReceived(Long ownerId);
        void onError(Throwable t);
    }

    public interface AccommodationsCallback {
        void onAccommodationsReceived(List<AccommodationSearchRequestDTO> accommodations);
        void onError(Throwable t);
    }

    public ReservationAdapter(Context context, List<ReservationGetFrontDTO> reservations, OnButtonClickListener listener){
        super(context, 0, reservations);
        this.context = context;
        this.reservations = reservations;
        this.buttonClickListener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.card_reservation, parent, false);
        }

        ReservationGetFrontDTO reservation = reservations.get(position);

        TextView tvReservationId = convertView.findViewById(R.id.tv_reservation_id);
        TextView tvAccommodationName = convertView.findViewById(R.id.tv_reservation_accommodation_name);
        TextView tvAccommodationAddress = convertView.findViewById(R.id.tv_reservation_address);
        TextView tvGuestName = convertView.findViewById(R.id.tv_reservation_guest_name);
        TextView tvGuests = convertView.findViewById(R.id.tv_reservation_guests);
        TextView tvStartDate = convertView.findViewById(R.id.tv_reservation_start_date);
        TextView tvEndDate = convertView.findViewById(R.id.tv_reservation_end_date);
        TextView tvTotalPrice = convertView.findViewById(R.id.tv_reservation_total_price);
        TextView tvReservationStatus = convertView.findViewById(R.id.tv_reservation_status);
        Button btnCancelReservation = convertView.findViewById(R.id.btn_reservation_cancel);
        Button btnRateOwner = convertView.findViewById(R.id.btn_reservation_rate_owner);
        Button btnRateAcc = convertView.findViewById(R.id.btn_reservation_rate_accommodation);
        Button btnReport = convertView.findViewById(R.id.btn_reservation_report);

        tvReservationId.setText("Reservation ID: " + reservation.getId());
        tvAccommodationName.setText("Accommodation: " + reservation.getAccommodation().getName());
        tvAccommodationAddress.setText("Address: " + reservation.getAccommodation().getLocation().getAddress() + ", " + reservation.getAccommodation().getLocation().getCity());
        tvGuestName.setText("Guest: " + reservation.getGuest().getName() + " " + reservation.getGuest().getSurname());
        tvGuests.setText("Number of guests: " + String.valueOf(reservation.getNumberOfGuests()));
        tvStartDate.setText("Start date: " + reservation.getTimeSlot().getStartDate());
        tvEndDate.setText("End date: " + reservation.getTimeSlot().getEndDate());
        tvTotalPrice.setText("Total price: " + reservation.getTotalPrice());
        tvReservationStatus.setText("Status: " + reservation.getReservationStatus().toString());

        btnCancelReservation.setOnClickListener(v -> {
            showCancelConfirmationDialog(reservation.getId());
        });

        btnRateOwner.setOnClickListener(v -> {
            if (buttonClickListener != null) {
                buttonClickListener.onRateOwnerClick(reservation.getAccommodation().getId(), reservation.getGuest().getId());
            }
        });

        btnRateAcc.setOnClickListener(v -> {
            if (buttonClickListener != null) {
                buttonClickListener.onRateAccommodationClick(reservation.getGuest().getId(), reservation.getAccommodation().getId());
            }
        });

        btnReport.setOnClickListener(v -> {
            if (buttonClickListener != null) {
                buttonClickListener.onReportClick(reservation);
            }
        });



        return convertView;
    }


    private void showCancelConfirmationDialog(Long reservationId) {
        // Kreiramo AlertDialog za potvrdu otkazivanja rezervacije
        new AlertDialog.Builder(context)
                .setTitle("Cancel Reservation")
                .setMessage("Are you sure you want to cancel this reservation?")
                .setPositiveButton("Yes", (dialog, which) -> cancelReservation(reservationId))
                .setNegativeButton("No", null)
                .show();
    }

    private void cancelReservation(Long reservationId) {
        // Pozivanje Retrofit API poziva za otkazivanje rezervacije
        RequestBody emptyBody = RequestBody.create("", MediaType.parse("application/json"));
        ApiUtils.getReservationService().cancelReservation(reservationId, emptyBody).enqueue(new Callback<ReservationGetDTO>() {
            @Override
            public void onResponse(Call<ReservationGetDTO> call, Response<ReservationGetDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Otkazivanje rezervacije je uspešno
                    Toast.makeText(context, "Reservation canceled!", Toast.LENGTH_SHORT).show();
                } else {
                    // Otkazivanje nije uspelo
                    Toast.makeText(context, "You cannot cancel a completed or active reservation.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReservationGetDTO> call, Throwable t) {
                // Greška prilikom poziva
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

/*
    private void cancelReservation(Long reservationId) {
        // Pozivanje Retrofit API poziva za otkazivanje rezervacije
        RequestBody emptyBody = RequestBody.create("", MediaType.parse("application/json"));
        ApiUtils.getReservationService().cancelReservation(reservationId, emptyBody).enqueue(new Callback<ReservationGetDTO>() {
            @Override
            public void onResponse(Call<ReservationGetDTO> call, Response<ReservationGetDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Otkazivanje rezervacije je uspešno
                    Toast.makeText(context, "Reservation canceled!", Toast.LENGTH_SHORT).show();
                } else {
                    // Otkazivanje nije uspelo
                    Toast.makeText(context, "Failed to cancel reservation.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReservationGetDTO> call, Throwable t) {
                // Greška prilikom poziva
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

 */
}
