package com.example.bookingapp.models.accommodations;

import com.example.bookingapp.models.enums.TypeOfAccommodation;

import java.util.List;

public class AccommodationSearchRequestDTO {
    private Long id;
    private String name;
    private Location location;
    private List<Accessories> accessories;
    private int minGuests;
    private int maxGuests;
    private Photo photo;
    private String accommodationImageUrl;
    private List<TimeSlot> timeSlots;
    private double pricePerUnit;
    private double totalPrice;
    private double averageGrade;
    private TypeOfAccommodation typeOfAccommodation;

    public AccommodationSearchRequestDTO() {
    }

    public AccommodationSearchRequestDTO(Long id, String name, Location location, List<Accessories> accessories, int minGuests, int maxGuests, Photo photo, String accommodationImageUrl, List<TimeSlot> timeSlots, double pricePerUnit, double totalPrice, double averageGrade, TypeOfAccommodation typeOfAccommodation) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.accessories = accessories;
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
        this.photo = photo;
        this.accommodationImageUrl = accommodationImageUrl;
        this.timeSlots = timeSlots;
        this.pricePerUnit = pricePerUnit;
        this.totalPrice = totalPrice;
        this.averageGrade = averageGrade;
        this.typeOfAccommodation = typeOfAccommodation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public String getAccommodationImageUrl() {
        return accommodationImageUrl;
    }

    public void setAccommodationImageUrl(String accommodationImageUrl) {
        this.accommodationImageUrl = accommodationImageUrl;
    }

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public TypeOfAccommodation getTypeOfAccommodation() {
        return typeOfAccommodation;
    }

    public void setTypeOfAccommodation(TypeOfAccommodation typeOfAccommodation) {
        this.typeOfAccommodation = typeOfAccommodation;
    }
}
