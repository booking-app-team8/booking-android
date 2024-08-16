package com.example.bookingapp.services;

import com.example.bookingapp.models.AccommodationRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AccommodationApiService {
    @GET("/pendingStatus")
    Call<List<AccommodationRequest>> getPendingAccommodationRequests();
}
