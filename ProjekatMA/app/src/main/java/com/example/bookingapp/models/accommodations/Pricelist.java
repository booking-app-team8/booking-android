package com.example.bookingapp.models.accommodations;

import java.time.LocalDate;

public class Pricelist {
    public String startDate, endDate;
    public double price;

    public Pricelist(String startDate, String endDate, double price) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }
}