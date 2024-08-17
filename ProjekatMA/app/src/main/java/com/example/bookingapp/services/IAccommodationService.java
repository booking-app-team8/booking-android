package com.example.bookingapp.services;

import com.example.bookingapp.models.accommodations.AccommodationSearchRequestDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IAccommodationService {
    @GET("searchAccommodations")
    Call<List<AccommodationSearchRequestDTO>> searchAccommodations(
            @Query("guests") Integer guests,
            @Query("location") String location,
            @Query("startDate") String startDate,
            @Query("endDate") String endDate
    );
}
