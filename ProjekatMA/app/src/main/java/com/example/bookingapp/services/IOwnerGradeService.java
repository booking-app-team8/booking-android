package com.example.bookingapp.services;

import com.example.bookingapp.dtos.UserPOSTDTO;
import com.example.bookingapp.models.OwnerGrade;
import com.example.bookingapp.models.reservations.ReservationGetFrontDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IOwnerGradeService {

    @Headers("Content-Type: application/json")
    @POST("addNew")
    Call<OwnerGrade> addNew(@Body OwnerGrade ownerGrade);

    @Headers("Content-Type: application/json")
    @GET("ownerGrades/{id}")
    Call<List<OwnerGrade>> getGradesByOwnerId(@Path("id") Long id);

    @DELETE("delete/{id}")
    Call<Boolean> deleteOwnerGrade(@Path("id") Long id);


    @Headers("Content-Type: application/json")
    @PUT("report/{id}")
    Call<Boolean> reportOwnerGrade(@Path("id") Long id);

}

