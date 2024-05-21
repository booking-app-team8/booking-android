package com.example.bookingapp.dtos;

public class LoginPOSTDTO {

    private String email;
    private String password;


    public LoginPOSTDTO() {}
    public LoginPOSTDTO(String _username, String _password) {
        this.email = _username;
        this.password = _password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
