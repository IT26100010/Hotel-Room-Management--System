package com.hotel.model;

public class LaundryService extends HotelService {
    public LaundryService() {}

    @Override
    public String getServiceType() { return "LAUNDRY"; }
    public LaundryService(String id, String name, String description, double price, String availability) {
        super(id, name, description, price, availability);
    }

    @Override
    public double calculateCost(int quantity) {
        return super.calculateCost(quantity);
    }
}
