package com.example.bookingapp.services;

import com.example.bookingapp.models.grades.OwnerGrade;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IGradesService {
    @GET("ownerGrades/getAllOwnersGrades")
    Call<List<OwnerGrade>> getAllOwnerGrades();
}
