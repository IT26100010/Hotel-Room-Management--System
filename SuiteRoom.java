package com.hotel.model;

public class SuiteRoom extends Room {
    private boolean livingArea;

    public SuiteRoom() {}

    public SuiteRoom(String id, String number, double price, int capacity,
                     String availability, boolean livingArea) {
        super(id, number, price, capacity, availability);
        this.livingArea = livingArea;
    }

    @Override
    public String getRoomType() { return "SUITE"; }

    @Override
    public double calculatePrice(int nights) {
        double total = getBasePrice() * nights;
        return livingArea ? total + (2500.0 * nights) : total;
    }

    public boolean isLivingArea() { return livingArea; }
    public void setLivingArea(boolean livingArea) { this.livingArea = livingArea; }
}
