package com.example.bookingapp.utils;


import com.example.bookingapp.services.IAccessoriesService;
import com.example.bookingapp.services.IAccommodationService;
import com.example.bookingapp.services.IUserService;

//192.168.1.5
public class ApiUtils {

//    public static final String BASE_URL = "http://192.168.1.5:8081/api/"; // NOVI SAD WIFI;
    public static final String BASE_URL = "http://192.168.0.15:8081/api/"; //zr
//    public static final String BASE_URL = "http://192.168.1.7:8081/api/";

    public static IUserService getUserService(){
        return ApiClient.getClient(BASE_URL).create(IUserService.class);
    }

    public static IAccessoriesService getAccessoriesService() {
        return ApiClient.getClient(BASE_URL).create(IAccessoriesService.class);
    }

    public static IAccommodationService getAccommodationService() {
        return ApiClient.getClient(BASE_URL).create(IAccommodationService.class);
    }



}
