package com.hotel.model;

public class HotelService {
    private String serviceId;
    private String serviceName;
    private String description;
    private double price;
    private String availability;

    public HotelService() {}

    public HotelService(String serviceId, String serviceName, String description,
                        double price, String availability) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.description = description;
        this.price = price;
        this.availability = availability;
    }

    public String getServiceType() { return "GENERAL"; }

    public double calculateCost(int quantity) {
        return price * quantity;
    }

    public String getServiceId() { return serviceId; }
    public void setServiceId(String v) { serviceId = v; }
    public String getServiceName() { return serviceName; }
    public void setServiceName(String v) { serviceName = v; }
    public String getDescription() { return description; }
    public void setDescription(String v) { description = v; }
    public double getPrice() { return price; }
    public void setPrice(double v) { price = v; }
    public String getAvailability() { return availability; }
    public void setAvailability(String v) { availability = v; }
}
