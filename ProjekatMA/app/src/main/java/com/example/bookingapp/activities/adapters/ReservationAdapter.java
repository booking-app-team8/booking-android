package com.example.bookingapp.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.bookingapp.R;
import com.example.bookingapp.dtos.ReservationGetDTO;
import com.example.bookingapp.models.enums.ReservationStatus;
import com.example.bookingapp.models.reservations.ReservationGetFrontDTO;
import com.example.bookingapp.utils.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationAdapter extends ArrayAdapter<ReservationGetFrontDTO> {

    private Context context;
    private List<ReservationGetFrontDTO> reservations;


    public ReservationAdapter(Context context, List<ReservationGetFrontDTO> reservations){
        super(context, 0, reservations);
        this.context = context;
        this.reservations = reservations;
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
        TextView tvGuestName = convertView.findViewById(R.id.tv_reservation_guest_name);
        TextView tvStartDate = convertView.findViewById(R.id.tv_reservation_start_date);
        TextView tvEndDate = convertView.findViewById(R.id.tv_reservation_end_date);
        TextView tvTotalPrice = convertView.findViewById(R.id.tv_reservation_total_price);
        TextView tvReservationStatus = convertView.findViewById(R.id.tv_reservation_status);
        Button btnCancelReservation = convertView.findViewById(R.id.btn_reservation_cancel);
        Button btnRateOwner = convertView.findViewById(R.id.btn_reservation_rate_owner);

        tvReservationId.setText("Reservation ID: " + reservation.getId());
        tvAccommodationName.setText("Accommodation: " + reservation.getAccommodation().getName());
        tvGuestName.setText("Guest: " + reservation.getGuest().getName() + " " + reservation.getGuest().getSurname());
        tvStartDate.setText("Start date: " + reservation.getTimeSlot().getStartDate());
        tvEndDate.setText("End date: " + reservation.getTimeSlot().getEndDate());
        tvTotalPrice.setText("Total price: " + reservation.getTotalPrice());
        tvReservationStatus.setText("Status: " + reservation.getReservationStatus().toString());

        btnCancelReservation.setOnClickListener(v -> {
            cancelReservation(reservation.getId());
        });

        btnRateOwner.setOnClickListener(v -> {
            rateOwner(reservation.getAccommodation().getId());
        });



        return convertView;
    }

    private void rateOwner(Long id) {
        Toast.makeText(context, "id"+id, Toast.LENGTH_SHORT).show();
        //PREKO ACCOMMODATION ID nadjem ko je vlasnik a preko vlasnika nadjem sve njegove accommodatione
        //ONDA KAD IMAM SVE SMESTAJE OD VLASNIKA IZABRANOG SMESTAJA GLEDAM DA LI U REZERVACIJAMA
        //IMA NEKI SMESTAJ SA TIM ID-JEM I ONDA AKO IMA GLEDAM DA LI JE FINISHED SAMO TOG VLASNIKA MOGU DA OCENIM
        
        for (ReservationGetFrontDTO reservation: this.reservations) {

//            if (reservation.ge)
            if (reservation.getReservationStatus().equals(ReservationStatus.FINISHED)) {
                //moze da se oceni onda cim naleti na makar jednu zavrsenu
                break;
            }
        }


    }

    private void cancelReservation(Long reservationId) {
        // Pozivanje Retrofit API poziva za otkazivanje rezervacije
        ApiUtils.getReservationService().cancelReservation(reservationId).enqueue(new Callback<ReservationGetDTO>() {
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
}
