package com.example.bookingapp.dtos;

import com.example.bookingapp.models.enums.Role;

public class UserPutDTO {
    private String name;
    private String surname;
    private String address;
    private String phoneNumber;
    private String password;

    public UserPutDTO() {
    }

    public UserPutDTO(String name, String surname, String address, String phoneNumber, String password) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
