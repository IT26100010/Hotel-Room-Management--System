package com.hotel.model;

public abstract class Payment {
    private String paymentId;
    private String reservationId;
    private double amount;
    private String paymentDate;
    private String paymentStatus;

    protected Payment() {}

    protected Payment(String paymentId, String reservationId, double amount,
                      String paymentDate, String paymentStatus) {
        this.paymentId = paymentId;
        this.reservationId = reservationId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
    }

    public abstract String getPaymentMethod();
    public abstract boolean processPayment();

    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String v) { paymentId = v; }
    public String getReservationId() { return reservationId; }
    public void setReservationId(String v) { reservationId = v; }
    public double getAmount() { return amount; }
    public void setAmount(double v) { amount = v; }
    public String getPaymentDate() { return paymentDate; }
    public void setPaymentDate(String v) { paymentDate = v; }
    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String v) { paymentStatus = v; }
}
