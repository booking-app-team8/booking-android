package com.example.bookingapp.services;

import com.example.bookingapp.dtos.LoginGETDTO;
import com.example.bookingapp.dtos.LoginPOSTDTO;
import com.example.bookingapp.dtos.UserPOSTDTO;
import com.example.bookingapp.models.users.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IUserService {
    @Headers("Content-Type: application/json")
    @POST("login")
    Call<LoginGETDTO> login(@Body LoginPOSTDTO loginRequest);

    @Headers("Content-Type: application/json")
    @GET("{email}")
    Call<User> findUser(@Path("email") String email);

    @Headers("Content-Type: application/json")
    @POST("register")
    Call<String> register(@Body UserPOSTDTO userPOSTDTO);


}
