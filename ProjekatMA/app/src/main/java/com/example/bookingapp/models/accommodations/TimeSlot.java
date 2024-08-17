package com.example.bookingapp.models.accommodations;

import java.time.LocalDate;

public class TimeSlot {
    private Long id;
    private String startDate;
    private String endDate;
    private boolean deleted;

    public TimeSlot() {
    }

    public TimeSlot(Long id, String startDate, String endDate, boolean deleted) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
