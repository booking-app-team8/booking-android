package com.example.bookingapp.models.reservations;

import com.example.bookingapp.models.accommodations.AccommodationSearchRequestDTO;
import com.example.bookingapp.models.accommodations.TimeSlot;
import com.example.bookingapp.models.enums.ReservationStatus;
import com.example.bookingapp.models.users.Guest;

public class ReservationGetFrontDTO {
    private Long id;
    private TimeSlot timeSlot;
    private AccommodationSearchRequestDTO accommodation;
    private Long numberOfGuests;
    private Guest guest;
    private ReservationStatus reservationStatus;
    private Long numberOfGuestCancellation;
    private double totalPrice;

    public ReservationGetFrontDTO() {
    }

    public ReservationGetFrontDTO(Long id, TimeSlot timeSlot, AccommodationSearchRequestDTO accommodation, Long numberOfGuests, Guest guest, ReservationStatus reservationStatus, Long numberOfGuestCancellation, double totalPrice) {
        this.id = id;
        this.timeSlot = timeSlot;
        this.accommodation = accommodation;
        this.numberOfGuests = numberOfGuests;
        this.guest = guest;
        this.reservationStatus = reservationStatus;
        this.numberOfGuestCancellation = numberOfGuestCancellation;
        this.totalPrice = totalPrice;
    }

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

    public AccommodationSearchRequestDTO getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(AccommodationSearchRequestDTO accommodation) {
        this.accommodation = accommodation;
    }

    public Long getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(Long numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public Long getNumberOfGuestCancellation() {
        return numberOfGuestCancellation;
    }

    public void setNumberOfGuestCancellation(Long numberOfGuestCancellation) {
        this.numberOfGuestCancellation = numberOfGuestCancellation;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
