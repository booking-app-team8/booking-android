package com.example.bookingapp.dtos;

import com.example.bookingapp.models.accommodations.Accessories;
import com.example.bookingapp.models.accommodations.Location;
import com.example.bookingapp.models.accommodations.Photo;
import com.example.bookingapp.models.accommodations.PriceUnit;
import com.example.bookingapp.models.accommodations.Pricelist;
import com.example.bookingapp.models.accommodations.TimeSlot;
import com.example.bookingapp.models.accommodations.TypeOfAccommodation;

import java.util.List;

public class AccommodationPostDTO {

    public String name;
    public String description;
    public Location location;
    public List<Accessories> accessories;
    public int minGuests;
    public int maxGuests;
    public PriceUnit priceUnit;
    public List<Photo> images;
    public TypeOfAccommodation typeOfAccommodation;
    public List<TimeSlot> timeSlots;
    public List<Pricelist> priceLists;
    public String email;
    public int cancellationDaysInAdvance;
    public double defaultedPrice;

    public AccommodationPostDTO(String name, String description, Location location, List<Accessories> accessories, int minGuests, int maxGuests, PriceUnit priceUnit, List<Photo> imagesUrl, TypeOfAccommodation typeOfAccommodation, List<TimeSlot> timeSlot, List<Pricelist> priceList, int cancellationDaysInAdvance, double defaultedPrice) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.accessories = accessories;
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
        this.priceUnit = priceUnit;
        this.images = imagesUrl;
        this.typeOfAccommodation = typeOfAccommodation;
        this.timeSlots = timeSlot;
        this.priceLists = priceList;
        this.cancellationDaysInAdvance = cancellationDaysInAdvance;
        this.defaultedPrice = defaultedPrice;
    }

    public AccommodationPostDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public double getDefaultedPrice() {
        return defaultedPrice;
    }

    public void setDefaultedPrice(double defaultedPrice) {
        this.defaultedPrice = defaultedPrice;
    }
}
