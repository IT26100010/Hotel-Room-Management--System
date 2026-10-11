package com.hotel.model;

public abstract class Room {
    private String roomId;
    private String roomNumber;
    private double basePrice;
    private int capacity;
    private String availability;

    protected Room() {}

    protected Room(String roomId, String roomNumber, double basePrice,
                   int capacity, String availability) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.basePrice = basePrice;
        this.capacity = capacity;
        this.availability = availability;
    }

    public abstract String getRoomType();
    public abstract double calculatePrice(int nights);

    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public double getBasePrice() { return basePrice; }
    public void setBasePrice(double basePrice) { this.basePrice = basePrice; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public String getAvailability() { return availability; }
    public void setAvailability(String availability) { this.availability = availability; }
}
