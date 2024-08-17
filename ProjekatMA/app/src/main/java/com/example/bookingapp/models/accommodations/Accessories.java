package com.example.bookingapp.models.accommodations;

public class Accessories {
    public Long id;
    public String accessories;
    public boolean isSelected;
  
    public Accessories() {
    }

    /*
    public Accessories(Long id, String accessories) {
        this.id = id;
        this.accessories = accessories;
    }
     */

    public Accessories(Long id, String name) {
        this.id = id;
        this.accessories = name;
        this.isSelected = false;
    }

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
