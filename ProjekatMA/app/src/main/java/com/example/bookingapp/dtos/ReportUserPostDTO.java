package com.example.bookingapp.dtos;

public class ReportUserPostDTO {
    private String emailFrom;
    private String emailTo;
    private Long accommodationId;
    private String reason;

    public ReportUserPostDTO() {

    }

    public ReportUserPostDTO(String emailFrom, String emailTo, Long accommodationId, String reason) {
        this.emailFrom = emailFrom;
        this.emailTo = emailTo;
        this.accommodationId = accommodationId;
        this.reason = reason;
    }

    public String getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
