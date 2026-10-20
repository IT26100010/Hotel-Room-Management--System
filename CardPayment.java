package com.hotel.model;

public class CardPayment extends Payment {
    private String cardHolderName;
    private String lastFourDigits;

    public CardPayment() {}

    public CardPayment(String id, String reservationId, double amount, String date,
                       String status, String cardHolderName, String lastFourDigits) {
        super(id, reservationId, amount, date, status);
        this.cardHolderName = cardHolderName;
        this.lastFourDigits = lastFourDigits;
    }

    @Override public String getPaymentMethod() { return "CARD"; }
    @Override public boolean processPayment() { return true; }

    public String getCardHolderName() { return cardHolderName; }
    public void setCardHolderName(String v) { cardHolderName = v; }
    public String getLastFourDigits() { return lastFourDigits; }
    public void setLastFourDigits(String v) { lastFourDigits = v; }
}
