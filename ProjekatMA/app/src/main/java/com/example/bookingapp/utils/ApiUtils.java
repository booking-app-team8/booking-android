package com.example.bookingapp.utils;


import com.example.bookingapp.services.AccommodationRequestService;
import com.example.bookingapp.services.IUserService;

//192.168.1.5
public class ApiUtils {


    public static final String BASE_URL = "http://172.20.10.5:8081/api/";
    public static final String BASE_URL_2 = "http://172.20.10.5:8081/api/accommodations/";

//    public static final String BASE_URL = "http://192.168.1.5:8081/api/" NOVI SAD WIFI;
    //public static final String BASE_URL = "http://192.168.0.15:8081/api/";


    public static IUserService getUserService(){
        return ApiClient.getClient(BASE_URL).create(IUserService.class);
    }

    public static AccommodationRequestService getAccommodationRequestService(){
        return ApiClient.getClient(BASE_URL_2).create(AccommodationRequestService.class);
    }

}
