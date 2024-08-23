package com.example.bookingapp.services;

import com.example.bookingapp.dtos.grades.AccommodationCommentDTO;
import com.example.bookingapp.dtos.grades.OwnerCommentDTO;
import com.example.bookingapp.models.grades.OwnerGrade;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IGradesService {
    @GET("ownerGrades/getAllOwnersGrades")
    Call<List<OwnerGrade>> getAllOwnerGrades();

    @GET("accommodationGrades/getAllAccommodationGrades")
    Call<List<AccommodationCommentDTO>> getAllAccommodationGrades();

    @PUT("accommodationGrades/accept/{id}")
    Call<Boolean> acceptAccommodationGrade(@Path("id") Long id);

    @PUT("accommodationGrades/notReport/{id}")
    Call<Boolean> notReportAccommodationGrade(@Path("id") Long id);

    @GET("ownerGrades/getReport")
    Call<List<OwnerCommentDTO>> getReportOwnerGrade();

    @GET("accommodationGrades/getReported")
    Call<List<AccommodationCommentDTO>> getReportedAccommodationGrade();

    @DELETE("ownerGrades/delete/{id}")
    Call<Boolean> deleteOwnerGrade(@Path("id") Long id);

    @DELETE("accommodationGrades/delete/{id}")
    Call<Boolean> deleteAccommodationGrade(@Path("id") Long id);
}
