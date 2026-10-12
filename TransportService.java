package com.hotel.model;

public class TransportService extends HotelService {
    private String vehicleType;
    private String pickupLocation;

    public TransportService() {}

    @Override
    public String getServiceType() { return "TRANSPORT"; }
    public TransportService(String id, String name, String description, double price,
                            String availability, String vehicleType, String pickupLocation) {
        super(id, name, description, price, availability);
        this.vehicleType = vehicleType;
        this.pickupLocation = pickupLocation;
    }

    @Override
    public double calculateCost(int quantity) {
        return super.calculateCost(quantity) + 500.0;
    }

    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String v) { vehicleType = v; }
    public String getPickupLocation() { return pickupLocation; }
    public void setPickupLocation(String v) { pickupLocation = v; }
}
