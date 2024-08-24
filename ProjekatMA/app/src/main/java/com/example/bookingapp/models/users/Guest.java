package com.example.bookingapp.models.users;

import com.example.bookingapp.models.enums.Role;

public class Guest extends User{
    private boolean notificationStatus;

    public Guest(Long id, String name, String surname, String address, String phoneNumber, String email, Role role, boolean activationStatus, boolean isBlocked, boolean notificationStatus) {
        super(id, name, surname, address, phoneNumber, email, role, activationStatus, isBlocked);
        this.notificationStatus = true;
    }
}
