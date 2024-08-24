package com.example.bookingapp.services;

import com.example.bookingapp.models.AccommodationGrade;
import com.example.bookingapp.models.OwnerGrade;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IAccommodationGradeService {

    @Headers("Content-Type: application/json")
    @POST("addNew")
    Call<AccommodationGrade> addNew(@Body AccommodationGrade accommodationGrade);

    @Headers("Content-Type: application/json")
    @GET("getGradesByAccId/{id}")
    Call<List<AccommodationGrade>> getGradesByAccId(@Path("id") Long id);


    @DELETE("delete/{id}")
    Call<Boolean> deleteOwnerGrade(@Path("id") Long id);

    @Headers("Content-Type: application/json")
    @PUT("report/{id}")
    Call<Boolean> reportOwnerGrade(@Path("id") Long id);
}
