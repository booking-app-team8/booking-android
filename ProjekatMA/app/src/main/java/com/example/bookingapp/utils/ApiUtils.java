package com.example.bookingapp.utils;



import com.example.bookingapp.services.IAccessoriesService;

import com.example.bookingapp.services.IAccommodationGradeService;
import com.example.bookingapp.services.IAccommodationService;

import com.example.bookingapp.services.AccommodationRequestService;


import com.example.bookingapp.services.IGradesService;
import com.example.bookingapp.services.IGuestService;
import com.example.bookingapp.services.IOwnerService;
import com.example.bookingapp.services.IOwnerGradeService;
import com.example.bookingapp.services.IReservationRequestService;
import com.example.bookingapp.services.IReservationService;
import com.example.bookingapp.services.IUserReportsService;
import com.example.bookingapp.services.IUserService;

public class ApiUtils {


//    public static final String BASE_URL = "http://192.168.1.5:8081/api/"; // NOVI SAD WIFI;
    //public static final String BASE_URL = "http://192.168.0.15:8081/api/"; //zr
public static final String BASE_URL = "http://172.20.10.5:8081/api/";
    public static final String BASE_URL_OWNER_GRADES = BASE_URL + "ownerGrades/";
    public static final String BASE_URL_ACCOMMODATION_GRADES = BASE_URL + "accommodationGrades/";
//    public static final String BASE_URL = "http://192.168.1.7:8081/api/";



    public static final String BASE_URL_ACCOMMODATION = "http://172.20.10.5:8081/api/accommodations/";
    // LUKA
//        public static final String BASE_URL_ACCOMMODATION = "http://192.168.0.15:8081/api/accommodations/";

    public static final String BASE_URL_2 = "http://172.20.10.5:8081/api/accommodations/";
//     LUKA
//    public static final String BASE_URL_2 = "http://192.168.0.15:8081/api/accommodations/";

//    public static final String BASE_URL = "http://192.168.1.5:8081/api/" NOVI SAD WIFI;
    //public static final String BASE_URL = "http://192.168.0.15:8081/api/";


    public static IUserService getUserService(){
        return ApiClient.getClient(BASE_URL).create(IUserService.class);
    }

    public static IAccessoriesService getAccessoriesService() {
        return ApiClient.getClient(BASE_URL).create(IAccessoriesService.class);
    }

    public static IAccommodationService getIAccommodationService() {
        return ApiClient.getClient(BASE_URL).create(IAccommodationService.class);
    }


    public static IAccommodationService getAccommodationService(){
        return ApiClient.getClient(BASE_URL_ACCOMMODATION).create(IAccommodationService.class);
    }


    public static AccommodationRequestService getAccommodationRequestService(){
        return ApiClient.getClient(BASE_URL_2).create(AccommodationRequestService.class);
    }

    public static IReservationService getReservationService(){
        return ApiClient.getClient(BASE_URL).create(IReservationService.class);
    }

    public static IUserReportsService getUserReportsService(){
        return ApiClient.getClient(BASE_URL).create(IUserReportsService.class);
    }

    public static IGradesService getGradesService(){
        return ApiClient.getClient(BASE_URL).create(IGradesService.class);
    }

    public static IOwnerService getOwnerService(){
        return ApiClient.getClient(BASE_URL).create(IOwnerService.class);
    }

    public static IGuestService getGuestService(){
        return ApiClient.getClient(BASE_URL).create(IGuestService.class);
    }

    public static IOwnerGradeService getOwnerGradeService(){
        return ApiClient.getClient(BASE_URL_OWNER_GRADES).create(IOwnerGradeService.class);
    }

    public static IAccommodationGradeService getIAccommodationGradeService() {
        return ApiClient.getClient(BASE_URL_ACCOMMODATION_GRADES).create(IAccommodationGradeService.class);
    }

    public static IReservationRequestService getReservationRequestService(){
        return ApiClient.getClient(BASE_URL).create(IReservationRequestService.class);
    }
}
