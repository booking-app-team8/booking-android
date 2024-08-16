package com.example.bookingapp.models.accommodations;

public class Location {
    public String address;
    public String city;
    public String country;
    public double longitude;
    public double latitude;

    public Location(String _address, String _city, String _country, double _longitude, double _latitude) {
        this.address = _address;
        this.city = _city;
        this.country = _country;
        this.longitude = _longitude;
        this.latitude = _latitude;
    }

}
