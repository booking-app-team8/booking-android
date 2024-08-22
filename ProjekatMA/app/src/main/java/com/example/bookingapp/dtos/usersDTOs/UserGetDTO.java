package com.example.bookingapp.dtos.usersDTOs;

import com.example.bookingapp.models.enums.Role;

public class UserGetDTO {
    private String name;
    private String surname;
    private String address;
    private String phoneNumber;
    private String email;
    private Role role;
    private boolean activationStatus;
    private boolean isBlocked;

    public UserGetDTO() {
    }

    public UserGetDTO(String name, String surname, String address, String phoneNumber, String email, Role role, boolean activationStatus, boolean isBlocked) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
        this.activationStatus = activationStatus;
        this.isBlocked = isBlocked;
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

    public boolean isActivationStatus() {
        return activationStatus;
    }

    public void setActivationStatus(boolean activationStatus) {
        this.activationStatus = activationStatus;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }
}
