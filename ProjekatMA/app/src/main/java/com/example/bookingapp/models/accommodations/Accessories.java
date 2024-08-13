package com.example.bookingapp.models.accommodations;

public class Accessories {
    public Long id;
    public String accessories;
    public boolean isSelected;

    public Accessories(Long id, String name) {
        this.id = id;
        this.accessories = name;
        this.isSelected = false;
    }

    // Getteri i setteri
    public Long getId() {
        return id;
    }

    public String getName() {
        return accessories;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
