package com.hotel.model;

public class StandardRoom extends Room {
    public StandardRoom() {}

    public StandardRoom(String id, String number, double price, int capacity, String availability) {
        super(id, number, price, capacity, availability);
    }

    @Override
    public String getRoomType() { return "STANDARD"; }

    @Override
    public double calculatePrice(int nights) {
        return getBasePrice() * nights;
    }
}
