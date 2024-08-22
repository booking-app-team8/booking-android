package com.example.bookingapp.models.reservations;

import com.example.bookingapp.models.accommodations.Accommodation;
import com.example.bookingapp.models.accommodations.TimeSlot;
import com.example.bookingapp.models.enums.ReservationStatus;
import com.example.bookingapp.models.users.Guest;

public class Reservation {
    private Long id;
    private Guest guest;
    private TimeSlot timeSlot;
    private Accommodation accommodation;
    private Long numberOfGuests;
    private ReservationStatus reservationStatus;
    private double totalPrice;
    private boolean deleted;

    public Reservation() {
    }

    public Reservation(Long id, Guest guest, TimeSlot timeSlot, Accommodation accommodation, Long numberOfGuests, ReservationStatus reservationStatus, double totalPrice, boolean deleted) {
        this.id = id;
        this.guest = guest;
        this.timeSlot = timeSlot;
        this.accommodation = accommodation;
        this.numberOfGuests = numberOfGuests;
        this.reservationStatus = reservationStatus;
        this.totalPrice = totalPrice;
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
