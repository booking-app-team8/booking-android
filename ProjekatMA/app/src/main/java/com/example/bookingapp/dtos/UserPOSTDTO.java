package com.example.bookingapp.dtos;

import com.example.bookingapp.models.enums.Role;
import com.example.bookingapp.models.users.User;

public class UserPOSTDTO {

    private String name;
    private String surname;
    private String address;
    private String phoneNumber;
    private String email;
    private Role role;
    private String password;

    public UserPOSTDTO(){}

    public UserPOSTDTO(String name, String surname, String address, String phoneNumber, String email, Role role, String password) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
