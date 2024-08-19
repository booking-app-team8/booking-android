package com.example.bookingapp.dtos;

import com.example.bookingapp.models.accommodations.Accommodation;
import com.example.bookingapp.models.accommodations.TimeSlot;
import com.example.bookingapp.models.enums.ReservationStatus;

public class ReservationGetDTO {
    private Long id;
    private TimeSlot timeSlot;
    private Accommodation accommodation;
    private Long numberOfGuests;
    private ReservationStatus reservationStatus;
    private double totalPrice;

    // Default constructor
    public ReservationGetDTO() {}

    // Constructor with all fields
    public ReservationGetDTO(Long id, TimeSlot timeSlot, Accommodation accommodation, Long numberOfGuests,
                             ReservationStatus reservationStatus, double totalPrice) {
        this.id = id;
        this.timeSlot = timeSlot;
        this.accommodation = accommodation;
        this.numberOfGuests = numberOfGuests;
        this.reservationStatus = reservationStatus;
        this.totalPrice = totalPrice;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public Long getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(Long numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
