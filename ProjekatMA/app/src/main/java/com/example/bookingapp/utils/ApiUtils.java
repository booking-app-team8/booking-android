package com.example.bookingapp.utils;


import com.example.bookingapp.services.IUserService;

//192.168.1.5
public class ApiUtils {

    public static final String BASE_URL = "http://172.20.10.5:8081/api/";

    public static IUserService getUserService(){
        return ApiClient.getClient(BASE_URL).create(IUserService.class);
    }



}
