package com.example.bookingapp.models.accommodations;

public class Accessories {
    private Long id;
    private String accessories;

    public Accessories() {
    }

    public Accessories(Long id, String accessories) {
        this.id = id;
        this.accessories = accessories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccessories() {
        return accessories;
    }

    public void setAccessories(String accessories) {
        this.accessories = accessories;
    }
}
