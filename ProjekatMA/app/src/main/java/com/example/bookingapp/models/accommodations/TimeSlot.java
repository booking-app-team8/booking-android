package com.example.bookingapp.models.accommodations;

import java.time.LocalDate;

public class TimeSlot {
    public String startDate;
    public String endDate;

    public TimeSlot(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getteri i setteri
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
}
