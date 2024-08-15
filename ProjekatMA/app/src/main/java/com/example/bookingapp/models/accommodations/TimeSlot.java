package com.example.bookingapp.models.accommodations;

import java.time.LocalDate;

public class TimeSlot {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean deleted;

    public TimeSlot() {
    }

    public TimeSlot(Long id, LocalDate startDate, LocalDate endDate, boolean deleted) {
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
