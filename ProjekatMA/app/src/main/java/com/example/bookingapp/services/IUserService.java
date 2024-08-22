package com.example.bookingapp.services;

import com.example.bookingapp.dtos.LoginGETDTO;
import com.example.bookingapp.dtos.LoginPOSTDTO;
import com.example.bookingapp.dtos.UserDto;
import com.example.bookingapp.dtos.UserGetDTO;
import com.example.bookingapp.dtos.UserPOSTDTO;
import com.example.bookingapp.dtos.UserPutDTO;
import com.example.bookingapp.models.users.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
    //@Headers("Content-Type: application/json")
    @PUT("user/{id}")
    Call<User> updateUser(@Path("id") Long id, @Body UserPutDTO userPutDTO);

    @Headers("Content-Type: application/json")
    @GET("user/by-email/{email}")
    Call<UserDto> getUserByEmailAddress(@Path("email") String email);

    @DELETE("user/delete-account/{id}")
    Call<Void> deleteUserAccount(@Path("id") Long id);


//    user/{id}
    @Headers("Content-Type: application/json")
    @GET("findUser/{id}")
    Call<UserGetDTO> getUsers(@Path("id") Long id);
}
