package com.example.bookingapp.models;

import com.example.bookingapp.models.enums.ReportStatus;

import java.time.LocalDateTime;

public class AccommodationGrade {
    private Long id;
    private Long guestId;
    private Long accommodationId;
    private int grade;

    private String comment;
    private String time;
    private ReportStatus reportStatus;

    public AccommodationGrade(Long guestId, Long accommodationId, int grade, String comment, String time) {
        this.guestId = guestId;
        this.accommodationId = accommodationId;
        this.grade = grade;
        this.comment = comment;
        this.time = time;
    }

    public AccommodationGrade(Long guestId, Long accommodationId, int grade, String comment, String time, ReportStatus reportStatus) {
        this.guestId = guestId;
        this.accommodationId = accommodationId;
        this.grade = grade;
        this.comment = comment;
        this.time = time;
        this.reportStatus = reportStatus;
    }

    public AccommodationGrade(Long id, Long guestId, Long accommodationId, int grade, String comment, String time) {
        this.id = id;
        this.guestId = guestId;
        this.accommodationId = accommodationId;
        this.grade = grade;
        this.comment = comment;
        this.time = time;
    }

    public ReportStatus getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(ReportStatus reportStatus) {
        this.reportStatus = reportStatus;
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
