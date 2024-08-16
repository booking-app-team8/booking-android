package com.example.bookingapp.services;

import com.example.bookingapp.dtos.AccommodationPostDTO;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface IAccommodationService {

    @Multipart
//    @Headers("Content-Type: application/json")
    @POST("accommodations/uploadImages/{accommodationId}")
    Call<Void> uploadImages(@Path("accommodationId") Long accommodationId,@Part MultipartBody.Part[] imageFiles);

    @Headers("Content-Type: application/json")
    @POST("accommodations")
    Call<Long> create(@Body AccommodationPostDTO accommodationPostDTO);
}
