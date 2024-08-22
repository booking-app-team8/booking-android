package com.example.bookingapp.services;

import com.example.bookingapp.dtos.usersDTOs.UserGetDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IOwnerService {
    @GET("owners/get-owner/{id}")
    Call<UserGetDTO> getOwnerById(@Path("id") Long id);
}
