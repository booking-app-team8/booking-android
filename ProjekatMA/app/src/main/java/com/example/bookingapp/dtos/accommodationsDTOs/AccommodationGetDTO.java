package com.example.bookingapp.dtos.accommodationsDTOs;

import com.example.bookingapp.models.accommodations.Accessories;
import com.example.bookingapp.models.accommodations.Location;
import com.example.bookingapp.models.accommodations.PriceUnit;
import com.example.bookingapp.models.accommodations.TypeOfAccommodation;

import java.util.List;

public class AccommodationGetDTO {
    private String name;
    private Location location;
    private List<Accessories> accessories;
    private int minGuests;
    private int maxGuests;
    private PriceUnit priceUnit;
    private String imageUrl;
    private TypeOfAccommodation typeOfAccommodation;
    private double averageGrade;

    public AccommodationGetDTO() {
    }

    public AccommodationGetDTO(String name, Location location, List<Accessories> accessories, int minGuests, int maxGuests, PriceUnit priceUnit, String imageUrl, TypeOfAccommodation typeOfAccommodation, double averageGrade) {
        this.name = name;
        this.location = location;
        this.accessories = accessories;
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
        this.priceUnit = priceUnit;
        this.imageUrl = imageUrl;
        this.typeOfAccommodation = typeOfAccommodation;
        this.averageGrade = averageGrade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Accessories> getAccessories() {
        return accessories;
    }

    public void setAccessories(List<Accessories> accessories) {
        this.accessories = accessories;
    }

    public int getMinGuests() {
        return minGuests;
    }

    public void setMinGuests(int minGuests) {
        this.minGuests = minGuests;
    }

    public int getMaxGuests() {
        return maxGuests;
    }

    public void setMaxGuests(int maxGuests) {
        this.maxGuests = maxGuests;
    }

    public PriceUnit getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(PriceUnit priceUnit) {
        this.priceUnit = priceUnit;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public TypeOfAccommodation getTypeOfAccommodation() {
        return typeOfAccommodation;
    }

    public void setTypeOfAccommodation(TypeOfAccommodation typeOfAccommodation) {
        this.typeOfAccommodation = typeOfAccommodation;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }
}
