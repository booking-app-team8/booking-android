package com.example.bookingapp.dtos;

public class LoginGETDTO {

    private String token;

    public LoginGETDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
