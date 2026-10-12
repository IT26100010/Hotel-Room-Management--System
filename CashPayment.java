package com.hotel.model;

public class CashPayment extends Payment {
    public CashPayment() {}
    public CashPayment(String id, String reservationId, double amount, String date, String status) {
        super(id, reservationId, amount, date, status);
    }
    @Override public String getPaymentMethod() { return "CASH"; }
    @Override public boolean processPayment() { return true; }
}
