package com.example.bookingapp.services;

import com.example.bookingapp.models.AccommodationRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AccommodationRequestService {
    @GET("pendingStatus")
    Call<List<AccommodationRequest>> getPendingAccommodationRequests();

    @GET("acceptStatus/{id}")
    Call<Void> acceptAccommodationStatus(@Path("id") Long id);

    @GET("rejectStatus/{id}")
    Call<Void> rejectAccommodationStatus(@Path("id") Long id);
}
