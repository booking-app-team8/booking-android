package com.example.bookingapp.services;

import com.example.bookingapp.dtos.grades.AccommodationCommentDTO;
import com.example.bookingapp.models.grades.OwnerGrade;

import java.util.List;

import retrofit2.Call;
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
}
