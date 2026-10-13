package com.hotel.model;

public class DeluxeRoom extends Room {
    private boolean balcony;

    public DeluxeRoom() {}

    public DeluxeRoom(String id, String number, double price, int capacity,
                      String availability, boolean balcony) {
        super(id, number, price, capacity, availability);
        this.balcony = balcony;
    }

    @Override
    public String getRoomType() { return "DELUXE"; }

    @Override
    public double calculatePrice(int nights) {
        double total = getBasePrice() * nights;
        return balcony ? total + (1000.0 * nights) : total;
    }

    public boolean isBalcony() { return balcony; }
    public void setBalcony(boolean balcony) { this.balcony = balcony; }
}
