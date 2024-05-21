package com.example.bookingapp.utils;

import android.os.StrictMode;

import com.example.bookingapp.models.users.User;

import retrofit2.Call;
import retrofit2.Response;

public class AuthService {
    private static User currentUser;
    private static String accessToken;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static String getToken() {
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        AuthService.accessToken = accessToken;
    }

    public void getMyInfo(String email) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Call<User> currUserResponseCall = ApiUtils.getUserService().findUser(email);
        try {
            Response<User> response = currUserResponseCall.execute();
            currentUser = response.body();
        }
        catch(Exception ex){
            ex.printStackTrace();
    }
    }



    public static void logout() {
        accessToken = null;
//        currentUser = null;
    }
}
