package com.example.bookingapp.dtos.grades;

import com.example.bookingapp.models.accommodations.Accommodation;
import com.example.bookingapp.models.users.User;

public class AccommodationCommentDTO {

    private Long id;
    private User guest;
    private Accommodation accommodation;
    private int grade;
    private String comment;
    private String time;
    private boolean acceptedStatus;

    public AccommodationCommentDTO() {
    }

    public AccommodationCommentDTO(Long id, User guest, Accommodation accommodation, int grade, String comment, String time, boolean acceptedStatus) {
        this.id = id;
        this.guest = guest;
        this.accommodation = accommodation;
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

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
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
