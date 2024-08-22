package com.example.bookingapp.dtos.grades;

import com.example.bookingapp.models.users.User;

public class OwnerCommentDTO {
    private Long id;
    private User guest;
    private User owner;
    private int grade;
    private String comment;
    private String time;
    private boolean acceptedStatus;

    public OwnerCommentDTO() {
    }

    public OwnerCommentDTO(Long id, User guest, User owner, int grade, String comment, String time, boolean acceptedStatus) {
        this.id = id;
        this.guest = guest;
        this.owner = owner;
        this.grade = grade;
        this.comment = comment;
        this.time = time;
        this.acceptedStatus = acceptedStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getGuest() {
        return guest;
    }

    public void setGuest(User guest) {
        this.guest = guest;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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

    public boolean isAcceptedStatus() {
        return acceptedStatus;
    }

    public void setAcceptedStatus(boolean acceptedStatus) {
        this.acceptedStatus = acceptedStatus;
    }
}
