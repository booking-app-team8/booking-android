package com.example.bookingapp.services;

import com.example.bookingapp.dtos.reservations.ReservationRequestsGetDTO;
import com.example.bookingapp.models.reservations.Reservation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IReservationRequestService {
    @Headers("Content-Type: application/json")
    @GET("reservationRequest/requests/{email}")
    Call<List<ReservationRequestsGetDTO>> getAllRequests(@Path("email") String email);

    @Headers("Content-Type: application/json")
    @POST("reservationRequest/accept/{id}")
    Call<Reservation> acceptReservationRequest(@Path("id") Long id);

    @POST("reservationRequest/reject/{id}")
    @Headers("Content-Type: application/json")
    Call<Void> rejectReservationRequest(@Path("id") Long id);
}
