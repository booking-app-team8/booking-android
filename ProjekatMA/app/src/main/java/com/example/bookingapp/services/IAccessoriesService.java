package com.example.bookingapp.services;

import com.example.bookingapp.models.accommodations.Accessories;
import com.example.bookingapp.models.users.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface IAccessoriesService {

    @Headers("Content-Type: application/json")
    @GET("accessories")
    Call<List<Accessories>> getAll();
}
