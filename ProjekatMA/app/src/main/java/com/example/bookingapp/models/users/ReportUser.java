package com.example.bookingapp.models.users;

public class ReportUser {
    private Long id;
    private Long idFrom;
    private Long idTo;
    private Long accommodationId;
    private String reasonReport;
    private boolean deleted;

    public ReportUser() {
    }

    public ReportUser(Long id, Long idFrom, Long idTo, Long accommodationId, String reasonReport, boolean deleted) {
        this.id = id;
        this.idFrom = idFrom;
        this.idTo = idTo;
        this.accommodationId = accommodationId;
        this.reasonReport = reasonReport;
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdFrom() {
        return idFrom;
    }

    public void setIdFrom(Long idFrom) {
        this.idFrom = idFrom;
    }

    public Long getIdTo() {
        return idTo;
    }

    public void setIdTo(Long idTo) {
        this.idTo = idTo;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
    }

    public String getReasonReport() {
        return reasonReport;
    }

    public void setReasonReport(String reasonReport) {
        this.reasonReport = reasonReport;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
