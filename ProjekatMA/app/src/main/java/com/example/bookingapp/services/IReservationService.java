package com.example.bookingapp.services;

import com.example.bookingapp.dtos.ReservationGetDTO;
import com.example.bookingapp.models.reservations.ReservationGetFrontDTO;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IReservationService {
    @GET("guestReservations/{email}")
    Call<List<ReservationGetFrontDTO>> getGuestReservations(@Path("email") String email);

    @PUT("cancelReservation/{id}")
    Call<ReservationGetDTO> cancelReservation(@Path("id") Long id, @Body RequestBody emptyBody);

    @GET("ownerReservations/{email}")
    Call<List<ReservationGetFrontDTO>> getOwnerReservations(@Path("email") String email);
}
