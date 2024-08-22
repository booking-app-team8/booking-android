package com.example.bookingapp.models;

import java.time.LocalDateTime;

public class AccommodationGrade {
    private Long id;
    private Long guestId;
    private Long accommodationId;
    private int grade;

    private String comment;
    private String time;

    public AccommodationGrade(Long guestId, Long accommodationId, int grade, String comment, String time) {
        this.guestId = guestId;
        this.accommodationId = accommodationId;
        this.grade = grade;
        this.comment = comment;
        this.time = time;
    }

    public AccommodationGrade(Long id, Long guestId, Long accommodationId, int grade, String comment, String time) {
        this.id = id;
        this.guestId = guestId;
        this.accommodationId = accommodationId;
        this.grade = grade;
        this.comment = comment;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
