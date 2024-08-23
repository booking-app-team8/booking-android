package com.example.bookingapp.services;

import com.example.bookingapp.dtos.ReportUserPostDTO;
import com.example.bookingapp.dtos.usersDTOs.BlockDTO;
import com.example.bookingapp.dtos.usersDTOs.ReportUserGetDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IUserReportsService {
    @GET("usersReportRequests")
    Call<List<ReportUserGetDTO>> getReportRequests();

    @PUT("user/block/{email}")
    Call<Void> blockUser(
            @Path("email") String email,
            @Body BlockDTO blockDTO
    );



}
