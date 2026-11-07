package com.hotel.model;

public class Reservation {
    private String reservationId;
    private String customerId;
    private String roomId;
    private String checkInDate;
    private String checkOutDate;
    private int numberOfGuests;
    private String status;
    private double totalAmount;

    public Reservation() {}

    public Reservation(String reservationId, String customerId, String roomId,
                       String checkInDate, String checkOutDate, int numberOfGuests,
                       String status, double totalAmount) {
        this.reservationId = reservationId;
        this.customerId = customerId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfGuests = numberOfGuests;
        this.status = status;
        this.totalAmount = totalAmount;
    }

    public String getReservationId() { return reservationId; }
    public void setReservationId(String v) { reservationId = v; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String v) { customerId = v; }
    public String getRoomId() { return roomId; }
    public void setRoomId(String v) { roomId = v; }
    public String getCheckInDate() { return checkInDate; }
    public void setCheckInDate(String v) { checkInDate = v; }
    public String getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(String v) { checkOutDate = v; }
    public int getNumberOfGuests() { return numberOfGuests; }
    public void setNumberOfGuests(int v) { numberOfGuests = v; }
    public String getStatus() { return status; }
    public void setStatus(String v) { status = v; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double v) { totalAmount = v; }
}
