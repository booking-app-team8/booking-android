package com.example.bookingapp.utils;


import com.example.bookingapp.services.IUserService;

//192.168.1.5
public class ApiUtils {

//    public static final String BASE_URL = "http://192.168.1.5:8081/api/" NOVI SAD WIFI;
    public static final String BASE_URL = "http://192.168.0.15:8081/api/";

    public static IUserService getUserService(){
        return ApiClient.getClient(BASE_URL).create(IUserService.class);
    }



}
