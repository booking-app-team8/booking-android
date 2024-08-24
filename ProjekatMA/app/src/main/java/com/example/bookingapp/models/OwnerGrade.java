package com.example.bookingapp.models;

import com.example.bookingapp.models.enums.ReportStatus;

import java.time.LocalDateTime;

public class OwnerGrade {

    private Long id;
    private Long guestId;
    private Long ownerId;
    private int grade;
    private String comment;
    private String time;

    private ReportStatus reportStatus;

    public OwnerGrade(Long id, Long guestId, Long ownerId, int grade, String comment, String time) {
        this.id = id;
        this.guestId = guestId;
        this.ownerId = ownerId;
        this.grade = grade;
        this.comment = comment;
        this.time = time;
    }

    public OwnerGrade(Long guestId, Long ownerId, int grade, String comment, String time) {
        this.guestId = guestId;
        this.ownerId = ownerId;
        this.grade = grade;
        this.comment = comment;
        this.time = time;
    }

    public OwnerGrade(Long guestId, Long ownerId, int grade, String comment, String time, ReportStatus reportStatus) {
        this.guestId = guestId;
        this.ownerId = ownerId;
        this.grade = grade;
        this.comment = comment;
        this.time = time;
        this.reportStatus = reportStatus;
    }

    public ReportStatus getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(ReportStatus reportStatus) {
        this.reportStatus = reportStatus;
    }

    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

