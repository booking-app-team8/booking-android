package com.example.bookingapp.models.accommodations;

import com.example.bookingapp.models.enums.AcceptanceStatus;
import com.example.bookingapp.models.enums.AccommodationStatus;

import java.util.List;

public class Accommodation {
    private Long id;
    private String name;
    private String description;
    private Location location;
    private List<Accessories> accessories;
    private int minGuests;
    private int maxGuests;
    private PriceUnit priceUnit;
    private List<Photo> images;
    private TypeOfAccommodation typeOfAccommodation;
    private List<TimeSlot> timeSlots;
    private List<Pricelist> priceLists;
    private int cancellationDaysInAdvance;
    private AccommodationStatus accommodationStatus;
    private AcceptanceStatus acceptanceStatus;
    private Long ownerId;
    private boolean deleted;
    private double defaultedPrice;

    // Constructors
    public Accommodation() {}

    public Accommodation(Long id, String name, String description, Location location, List<Accessories> accessories,
                         int minGuests, int maxGuests, PriceUnit priceUnit, List<Photo> images,
                         TypeOfAccommodation typeOfAccommodation, List<TimeSlot> timeSlots,
                         List<Pricelist> priceLists, int cancellationDaysInAdvance,
                         AccommodationStatus accommodationStatus, AcceptanceStatus acceptanceStatus,
                         Long ownerId, boolean deleted, double defaultedPrice) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.accessories = accessories;
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
        this.priceUnit = priceUnit;
        this.images = images;
        this.typeOfAccommodation = typeOfAccommodation;
        this.timeSlots = timeSlots;
        this.priceLists = priceLists;
        this.cancellationDaysInAdvance = cancellationDaysInAdvance;
        this.accommodationStatus = accommodationStatus;
        this.acceptanceStatus = acceptanceStatus;
        this.ownerId = ownerId;
        this.deleted = deleted;
        this.defaultedPrice = defaultedPrice;
    }

    // Getters and Setters

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<Photo> getImages() {
        return images;
    }

    public void setImages(List<Photo> images) {
        this.images = images;
    }

    public TypeOfAccommodation getTypeOfAccommodation() {
        return typeOfAccommodation;
    }

    public void setTypeOfAccommodation(TypeOfAccommodation typeOfAccommodation) {
        this.typeOfAccommodation = typeOfAccommodation;
    }

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }

    public List<Pricelist> getPriceLists() {
        return priceLists;
    }

    public void setPriceLists(List<Pricelist> priceLists) {
        this.priceLists = priceLists;
    }

    public int getCancellationDaysInAdvance() {
        return cancellationDaysInAdvance;
    }

    public void setCancellationDaysInAdvance(int cancellationDaysInAdvance) {
        this.cancellationDaysInAdvance = cancellationDaysInAdvance;
    }

    public AccommodationStatus getAccommodationStatus() {
        return accommodationStatus;
    }

    public void setAccommodationStatus(AccommodationStatus accommodationStatus) {
        this.accommodationStatus = accommodationStatus;
    }

    public AcceptanceStatus getAcceptanceStatus() {
        return acceptanceStatus;
    }

    public void setAcceptanceStatus(AcceptanceStatus acceptanceStatus) {
        this.acceptanceStatus = acceptanceStatus;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public double getDefaultedPrice() {
        return defaultedPrice;
    }

    public void setDefaultedPrice(double defaultedPrice) {
        this.defaultedPrice = defaultedPrice;
    }
}
