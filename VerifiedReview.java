package com.hotel.model;

public class VerifiedReview extends Review {
    private boolean verifiedBooking;

    public VerifiedReview() {}

    public VerifiedReview(String id, String customerId, String reservationId, int rating,
                          String comment, String date, boolean verifiedBooking) {
        super(id, customerId, reservationId, rating, comment, date);
        this.verifiedBooking = verifiedBooking;
    }

    @Override public String getReviewType() { return "VERIFIED"; }

    @Override public String displayReview() {
        return getRating() + "/5 - " + getComment() + " [Verified Guest]";
    }

    public boolean isVerifiedBooking() { return verifiedBooking; }
    public void setVerifiedBooking(boolean v) { verifiedBooking = v; }
}
