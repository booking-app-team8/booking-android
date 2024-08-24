package com.example.bookingapp.dtos.usersDTOs;

import com.example.bookingapp.dtos.accommodationsDTOs.AccommodationGetDTO;

public class ReportUserGetDTO {
    private Long id;
    private UserGetDTO from;
    private UserGetDTO to;
    private AccommodationGetDTO accommodationGetDTO;
    private String reason;

    public ReportUserGetDTO() {
    }

    public ReportUserGetDTO(Long id, UserGetDTO from, UserGetDTO to, AccommodationGetDTO accommodationGetDTO, String reason) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.accommodationGetDTO = accommodationGetDTO;
        this.reason = reason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserGetDTO getFrom() {
        return from;
    }

    public void setFrom(UserGetDTO from) {
        this.from = from;
    }

    public UserGetDTO getTo() {
        return to;
    }

    public void setTo(UserGetDTO to) {
        this.to = to;
    }

    public AccommodationGetDTO getAccommodationGetDTO() {
        return accommodationGetDTO;
    }

    public void setAccommodationGetDTO(AccommodationGetDTO accommodationGetDTO) {
        this.accommodationGetDTO = accommodationGetDTO;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
