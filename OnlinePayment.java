package com.hotel.model;

public class OnlinePayment extends Payment {
    private String transactionId;
    private String provider;

    public OnlinePayment() {}

    public OnlinePayment(String id, String reservationId, double amount, String date,
                         String status, String transactionId, String provider) {
        super(id, reservationId, amount, date, status);
        this.transactionId = transactionId;
        this.provider = provider;
    }

    @Override public String getPaymentMethod() { return "ONLINE"; }
    @Override public boolean processPayment() { return true; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String v) { transactionId = v; }
    public String getProvider() { return provider; }
    public void setProvider(String v) { provider = v; }
}
