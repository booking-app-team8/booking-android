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

import com.example.bookingapp.dtos.UserGetDTO;
import com.example.bookingapp.models.accommodations.AccommodationDTO;
import com.example.bookingapp.models.accommodations.AccommodationSearchRequestDTO;
import com.example.bookingapp.models.users.User;

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
  
   @Multipart
//    @Headers("Content-Type: application/json")
    @POST("accommodations/uploadImages/{accommodationId}")
    Call<Void> uploadImages(@Path("accommodationId") Long accommodationId,@Part MultipartBody.Part[] imageFiles);

    @Headers("Content-Type: application/json")
    @POST("accommodations")
    Call<Long> create(@Body AccommodationPostDTO accommodationPostDTO);

    @Headers("Content-Type: application/json")
    @GET("accommodations/forGradeOwner/{id}")
    Call<Long> forGradeOwner(@Path("id") Long id);

//    /ownerAccommodations/{id}
    @Headers("Content-Type: application/json")
    @GET("accommodations/ownerAccommodations/{id}")
    Call<List<AccommodationSearchRequestDTO>> getOwnerAccommodations(@Path("id") Long ownerId);

    @Headers("Content-Type: application/json")
    @GET("accommodations/getFullAccommodation/{id}")
    Call<AccommodationDTO> getFullAccommodation(@Path("id") Long ownerId);

    @GET("accommodations/allOwnerAccommodationsByAccId/{id}")
    Call<List<AccommodationSearchRequestDTO>> allOwnerAccommodations(@Path("id") Long id);

    @GET("accommodations/getOwnerByAccId/{id}")
    Call<UserGetDTO> getOwnerByAccId(@Path("id") Long id);


}
