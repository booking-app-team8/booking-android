package com.example.bookingapp.dtos;

import com.example.bookingapp.models.enums.Role;

public class UserDto {
    private Long id;
    private String name;
    private String surname;
    private String address;
    private String phoneNumber;
    private boolean isBlocked;
    private String email;
    private String password;
    private Role role;
    private boolean activationStatus;
    private boolean logOn;
    private boolean deleted;

    public UserDto(Long id, String name, String surname, String address, String phoneNumber, boolean isBlocked, String email, String password, Role role, boolean activationStatus, boolean logOn, boolean deleted) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.isBlocked = isBlocked;
        this.email = email;
        this.password = password;
        this.role = role;
        this.activationStatus = activationStatus;
        this.logOn = logOn;
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public boolean isLogOn() {
        return logOn;
    }

    public void setLogOn(boolean logOn) {
        this.logOn = logOn;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
