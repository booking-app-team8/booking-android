package com.example.bookingapp.dtos.reservations;

import com.example.bookingapp.models.accommodations.AccommodationSearchRequestDTO;
import com.example.bookingapp.models.enums.ReservationRequestStatus;
import com.example.bookingapp.models.users.Guest;

public class ReservationRequestsGetDTO {
    private Long id;
    private Guest guest;
    private String startDate;
    private String endDate;
    private AccommodationSearchRequestDTO accommodation;
    private Long numberOfGuests;
    private double totalPrice;
    private boolean deleted;
    private ReservationRequestStatus requestStatus;
    private Long numberOfGuestCancellation;

    public ReservationRequestsGetDTO() {
    }

    public ReservationRequestsGetDTO(Long id, Guest guest, String startDate, String endDate, AccommodationSearchRequestDTO accommodation, Long numberOfGuests, double totalPrice, boolean deleted, ReservationRequestStatus requestStatus, Long numberOfGuestCancellation) {
        this.id = id;
        this.guest = guest;
        this.startDate = startDate;
        this.endDate = endDate;
        this.accommodation = accommodation;
        this.numberOfGuests = numberOfGuests;
        this.totalPrice = totalPrice;
        this.deleted = deleted;
        this.requestStatus = requestStatus;
        this.numberOfGuestCancellation = numberOfGuestCancellation;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public ReservationRequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(ReservationRequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Long getNumberOfGuestCancellation() {
        return numberOfGuestCancellation;
    }

    public void setNumberOfGuestCancellation(Long numberOfGuestCancellation) {
        this.numberOfGuestCancellation = numberOfGuestCancellation;
    }
}
