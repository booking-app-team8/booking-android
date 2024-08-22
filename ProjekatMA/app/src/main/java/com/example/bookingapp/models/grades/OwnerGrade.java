package com.example.bookingapp.models.grades;

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
    private boolean acceptedStatus;
    private boolean deleted;

    public OwnerGrade() {
    }

    public OwnerGrade(Long id, Long guestId, Long ownerId, int grade, String comment, String time, ReportStatus reportStatus, boolean acceptedStatus, boolean deleted) {
        this.id = id;
        this.guestId = guestId;
        this.ownerId = ownerId;
        this.grade = grade;
        this.comment = comment;
        this.time = time;
        this.reportStatus = reportStatus;
        this.acceptedStatus = acceptedStatus;
        this.deleted = deleted;
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

    public ReportStatus getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(ReportStatus reportStatus) {
        this.reportStatus = reportStatus;
    }

    public boolean isAcceptedStatus() {
        return acceptedStatus;
    }

    public void setAcceptedStatus(boolean acceptedStatus) {
        this.acceptedStatus = acceptedStatus;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
