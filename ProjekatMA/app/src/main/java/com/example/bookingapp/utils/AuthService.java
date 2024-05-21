package com.example.bookingapp.utils;

import android.os.StrictMode;

import retrofit2.Call;
import retrofit2.Response;

public class AuthService {
//    private static User currentUser;
    private static String accessToken;

//    public static User getCurrentUser() {
//        return currentUser;
//    }

    public static String getToken() {
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        AuthService.accessToken = accessToken;
    }

    public void getMyInfo() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

//        Call<User> currUserResponseCall = ApiUtils.getUserService().getCurrentUser();
//        try {
//            Response<User> response = currUserResponseCall.execute();
//            currentUser = response.body();
//        }
//        catch(Exception ex){
//            ex.printStackTrace();
//    }
    }



    public static void logout() {
        accessToken = null;
//        currentUser = null;
    }
}
