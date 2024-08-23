package com.example.bookingapp.models.accommodations;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Accessories that = (Accessories) o;
        return id.equals(that.id);  // Assuming 'id' is a unique identifier for Accessories
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);  // Again, using 'id' for hashCode generation
    }

    // Override equals and hashCode to compare by relevant fields, like name or id
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Accessories that = (Accessories) o;
//
//        return Objects.equals(accessories, that.accessories);
//    }
//
//    @Override
//    public int hashCode() {
//        return accessories != null ? accessories.hashCode() : 0;
//    }


}
